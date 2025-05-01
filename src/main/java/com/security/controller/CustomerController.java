package com.security.controller;

import com.security.DTO.CustomerRequestDTO;
import com.security.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CustomerRequestDTO request) {
        customerService.save(request);
    }
}
