package com.security.controller;

import com.security.DTO.UserRequestDTO;
import com.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRequestDTO request) {
        userService.save(request);
    }
}
