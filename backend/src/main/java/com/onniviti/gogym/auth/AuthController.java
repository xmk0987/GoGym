package com.onniviti.gogym.auth;

import com.onniviti.gogym.user.User;
import com.onniviti.gogym.user.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Object[] result = authService.login(authRequest.username(), authRequest.password());

        String accessToken = (String) result[0];
        String refreshToken = (String) result[1];
        User user = (User) result[2];

        // Create UserDTO object to avoid sending sensitive information
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());

        // Set the refresh token in an HTTP-only, secure cookie
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // Use secure=true in production (for HTTPS)
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);

        // Create response body with access token and user info
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("accessToken", accessToken);
        responseBody.put("user", userDTO);

        // Return access token and user in the response body
        return ResponseEntity.ok(responseBody);
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


    // Check authentication status endpoint
    @GetMapping("/me")
    public ResponseEntity<?> checkAuthStatus(@RequestHeader("Authorization") String authHeader) {
        // Check if the Authorization header is valid
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        // Extract the token from the header
        String accessToken = authHeader.substring(7); // Remove "Bearer " prefix

        // Call the service to check authentication status
        Optional<UserDTO> userDTO = authService.checkAuthStatus(accessToken);

        // Handle the response based on whether the user was found or not
        if (userDTO.isPresent()) {
            return ResponseEntity.ok(userDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
        }
    }
}
