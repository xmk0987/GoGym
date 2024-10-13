package com.onniviti.gogym.registration;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class EmailValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public boolean test(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
