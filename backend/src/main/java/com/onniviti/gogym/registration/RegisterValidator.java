package com.onniviti.gogym.registration;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class RegisterValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Method to validate the entire registration request
    public Map<String, String> validateRegistrationRequest(RegistrationRequest request) {
        Map<String, String> errors = new HashMap<>();

        // First Name Validation
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            errors.put("firstName", "First name is required.");
        }

        // Last Name Validation
        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            errors.put("lastName", "Last name is required.");
        }

        // Email Validation
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            errors.put("email", "Email is required.");
        } else if (!isValidEmail(request.getEmail())) {
            errors.put("email", "Please enter a valid email address.");
        }

        // Password Validation
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.put("password", "Password is required.");
        } else if (request.getPassword().length() < 8) {
            errors.put("password", "Password must be at least 8 characters long.");
        }

        return errors;
    }

    // Utility method to validate email format
    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
