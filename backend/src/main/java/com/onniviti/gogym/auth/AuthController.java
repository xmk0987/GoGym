package com.onniviti.gogym.auth;

import com.onniviti.gogym.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            // Call the AuthService to handle login and generate the JWT tokens
            Object[] result = authService.login(authRequest.username(), authRequest.password());
            String accessToken = (String) result[0];
            String refreshToken = (String) result[1];
            User user = (User) result[2];

            // Set the accessToken and refreshToken as cookies
            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            accessTokenCookie.setHttpOnly(true);  // Prevent JavaScript access
            accessTokenCookie.setSecure(true);    // Ensure HTTPS is used
            accessTokenCookie.setPath("/");       // Path where cookie is available
            accessTokenCookie.setMaxAge(900);     // Expiration time in seconds (15 minutes)

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(604800);

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);


            return ResponseEntity.ok(Map.of(
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "email", user.getEmail()
            ));

        } catch (UsernameNotFoundException e) {
            // Return 404 Not Found if the user does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        } catch (IllegalStateException e) {
            // Return 400 Bad Request for invalid credentials
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");

        } catch (Exception e) {
            // Catch any other exceptions and return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> checkAuthStatus(HttpServletRequest request) {
        Optional<User> userOptional = authService.checkAuthStatus(request);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(Map.of(
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "email", user.getEmail()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }


}
