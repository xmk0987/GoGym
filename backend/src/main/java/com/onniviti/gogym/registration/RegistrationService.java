package com.onniviti.gogym.registration;

import com.onniviti.gogym.user.User;
import com.onniviti.gogym.user.UserRole;
import com.onniviti.gogym.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final UserService userService;

    public RegistrationService (EmailValidator emailValidator, UserService userService) {
        this.emailValidator = emailValidator;
        this.userService = userService;
    }

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }

        return userService.signUpUser(
                new User(request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER));
    }
}
