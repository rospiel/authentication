package com.security.decorators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EncryptPasswordDecorator {

    private final PasswordEncoder passwordEncoder;

    public String toEncrypt(String value) {
        return passwordEncoder.encode(value);
    }
}