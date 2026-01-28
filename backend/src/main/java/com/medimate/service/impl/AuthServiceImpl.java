package com.medimate.service.impl;

import com.medimate.dto.LoginDto;
import com.medimate.dto.RegisterDto;
import com.medimate.dto.UserDto;
import com.medimate.mapper.UserMapper;
import com.medimate.model.AppUser;
import com.medimate.model.Doctor;
import com.medimate.repository.AuthRepository;
import com.medimate.repository.DoctorRepository;
import com.medimate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final DoctorRepository doctorRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository,
                           DoctorRepository doctorRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder
    ) {
        this.authRepository = authRepository;
        this.doctorRepository = doctorRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(RegisterDto registerDto) {

        var appUser = new AppUser();
        appUser.setEmail(registerDto.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        appUser.setRole("USER");

        authRepository.save(appUser);

        Doctor doctor = new Doctor();
        doctor.setAppUser(appUser);
        doctor.setFullName(registerDto.getFullName());
        doctor.setSpecialization(registerDto.getSpecialization());

        doctorRepository.save(doctor);

        return userMapper.toUserDto(appUser, doctor);
    }

    @Override
    public UserDto login(LoginDto loginDto) {
        return null;
    }
}
