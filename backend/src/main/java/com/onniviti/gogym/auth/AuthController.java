package com.onniviti.gogym.auth;

import com.onniviti.gogym.user.User;
import com.onniviti.gogym.user.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            Object[] result = authService.login(authRequest.username(), authRequest.password());

            String accessToken = (String) result[0];
            String refreshToken = (String) result[1];
            User user = (User) result[2];

            UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(refreshTokenCookie);

            // Create response body with access token and user info
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("accessToken", accessToken);
            responseBody.put("user", userDTO);

            // Return access token and user in the response body
            return ResponseEntity.ok(responseBody);
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());

        } catch (UsernameNotFoundException ex) {
            System.out.println(ex.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    // Refresh endpoint to generate a new access token using the refresh token
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshToken) {
        // Call the service to refresh the token
        Optional<String> newAccessToken = authService.refreshAccessToken(refreshToken);

        // Handle response: if the token is valid, return the new access token as a Map
        return newAccessToken
                .map(token -> ResponseEntity.ok(Map.of("accessToken", token)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid or expired refresh token")));
    }


    @GetMapping("/me")
    public ResponseEntity<?> checkAuthStatus(@RequestHeader("Authorization") String authHeader) {
        // Check if the Authorization header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        // Extract the token from the header (remove "Bearer " prefix)
        String accessToken = authHeader.substring(7);

        // Call the service to check authentication status
        Optional<UserDTO> userDTO = authService.checkAuthStatus(accessToken);

        // If the token validation fails or the user is not found, return 401
        if (userDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
        }

        // If everything is valid, return the user data
        return ResponseEntity.ok(userDTO.get());
    }

}
