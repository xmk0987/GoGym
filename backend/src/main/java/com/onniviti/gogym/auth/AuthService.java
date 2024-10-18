package com.onniviti.gogym.auth;

import com.onniviti.gogym.auth.token.JwtTokenProvider;
import com.onniviti.gogym.user.User;
import com.onniviti.gogym.user.UserDTO;
import com.onniviti.gogym.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    // Method to check the authentication status
    public Optional<UserDTO> checkAuthStatus(String accessToken) {
        // Validate the token
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return Optional.empty(); // Return empty if token is invalid
        }

        // Extract user information from the token
        String username = jwtTokenProvider.getUserFromToken(accessToken);

        // Fetch the user from the database
        Optional<User> userOpt = userService.findUserByUsername(username);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        // Create and return a UserDTO
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        return Optional.of(userDTO);
    }

    // Method to refresh the access token
    public Optional<String> refreshAccessToken(String refreshToken) {
        // Validate the refresh token
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return Optional.empty(); // Return empty if the token is invalid
        }

        // Extract user information from the refresh token
        String username = jwtTokenProvider.getUserFromToken(refreshToken);

        // Check if the user exists
        Optional<User> userOpt = userService.findUserByUsername(username);
        if (userOpt.isEmpty()) {
            return Optional.empty(); // Return empty if the user doesn't exist
        }

        // Generate a new access token for the user
        User user = userOpt.get();
        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getEmail());

        // Return the new access token
        return Optional.of(newAccessToken);
    }
}
