package com.security.service;

import com.security.DTO.UserRequestDTO;
import com.security.entity.User;

import java.util.Optional;

public interface UserService {

    void save(UserRequestDTO request);
    Optional<User> searchBy(String email);
}
