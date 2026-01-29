package com.medimate.controller;

import com.medimate.dto.LoginDto;
import com.medimate.dto.RegisterDto;
import com.medimate.dto.UserDto;
import com.medimate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @GetMapping("/test")
    public String test() {
        return "Test Success";
    }
}
