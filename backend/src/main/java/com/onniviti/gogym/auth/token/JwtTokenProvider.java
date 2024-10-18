package com.onniviti.gogym.auth.token;

import com.onniviti.gogym.user.User;
import com.onniviti.gogym.user.UserService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessExpiration}")
    private long accessExpirationMs;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpirationMs;

    private Key signingKey;

    @Autowired
    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.signingKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public long getAccessExpirationMs() {
        return accessExpirationMs;
    }

    public void setAccessExpirationMs(long accessExpirationMs) {
        this.accessExpirationMs = accessExpirationMs;
    }

    public long getRefreshExpirationMs() {
        return refreshExpirationMs;
    }

    public void setRefreshExpirationMs(long refreshExpirationMs) {
        this.refreshExpirationMs = refreshExpirationMs;
    }

    // Generate access token
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    // Extract the username from the token
    public String getUserFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // Validate token and check if user exists
    public boolean validateTokenAndUser(String token) {
        if (validateToken(token)) {
            String username = getUserFromToken(token);

            // Check if the user exists in the database (you'll need to implement findUserByUsername)
            Optional<User> user = userService.findUserByUsername(username);

            if (user.isPresent()) {
                return true; // Token is valid, and user exists
            } else {
                System.out.println("User does not exist or is inactive");
                return false;
            }
        } else {
            return false; // Token is invalid
        }
    }
}
