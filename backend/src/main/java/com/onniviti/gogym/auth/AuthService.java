package com.onniviti.gogym.auth;

import com.onniviti.gogym.auth.token.JwtTokenProvider;
import com.onniviti.gogym.user.User;
import com.onniviti.gogym.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Object[] login(String username, String password) {
        try {
            // Validate the user credentials
            User user = userService.loginUser(username, password);

            // Generate access and refresh tokens
            String accessToken = jwtTokenProvider.generateAccessToken(username);
            String refreshToken = jwtTokenProvider.generateRefreshToken(username);

            // Return both tokens as an array
            return new Object[] { accessToken, refreshToken, user };

        } catch (AuthenticationException e) {
            // Log exception and rethrow
            System.err.println("Authentication error: " + e.getMessage());
            throw new UsernameNotFoundException("Invalid credentials");
        } catch (Exception e) {
            // Log any other potential errors
            System.err.println("Error generating tokens: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<User> checkAuthStatus(HttpServletRequest request) {
        String token = getTokenFromCookies(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getUserFromToken(token);
            System.out.println("Token valid. Extracted email: " + email);
            return userService.getUserByEmail(email);
        } else {
            System.out.println("Invalid or missing token.");
        }

        return Optional.empty();
    }


    // Helper method to get the accessToken from cookies
    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
