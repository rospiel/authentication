package com.security.mapper;

import com.security.DTO.UserRequestDTO;
import com.security.decorators.EncryptPasswordDecorator;
import com.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final EncryptPasswordDecorator encryptPasswordDecorator;

    public User toProductEntityBy(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encryptPasswordDecorator.toEncrypt(dto.getPassword()))
                .dateRegistration(OffsetDateTime.now())
                .build();
    }
}
