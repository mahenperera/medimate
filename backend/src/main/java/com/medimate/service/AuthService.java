package com.medimate.service;

import com.medimate.dto.LoginDto;
import com.medimate.dto.RegisterDto;
import com.medimate.dto.UserDto;

public interface AuthService {

    UserDto register(RegisterDto registerDto);

    UserDto login(LoginDto loginDto);
}
