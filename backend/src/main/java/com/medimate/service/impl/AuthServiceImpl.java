package com.medimate.service.impl;

import com.medimate.dto.LoginDto;
import com.medimate.dto.RegisterDto;
import com.medimate.dto.UserDto;
import com.medimate.exception.*;
import com.medimate.mapper.UserMapper;
import com.medimate.model.AppUser;
import com.medimate.model.Doctor;
import com.medimate.repository.AuthRepository;
import com.medimate.repository.DoctorRepository;
import com.medimate.config.JwtService;
import com.medimate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final DoctorRepository doctorRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository,
                           DoctorRepository doctorRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService
    ) {
        this.authRepository = authRepository;
        this.doctorRepository = doctorRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto register(RegisterDto registerDto) {

        if (authRepository.existsByEmail(registerDto.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + registerDto.getEmail());
        }

        var appUser = new AppUser();
        appUser.setEmail(registerDto.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        appUser.setRole("USER");

        AppUser savedUser = authRepository.save(appUser);

        Doctor doctor = new Doctor();
        doctor.setAppUser(appUser);
        doctor.setFullName(registerDto.getFullName());
        doctor.setSpecialization(registerDto.getSpecialization());

        doctorRepository.save(doctor);

        String token = jwtService.generateToken(savedUser);

        return userMapper.toUserDto(appUser, doctor, token, "Registration successful");
    }

    @Override
    public UserDto login(LoginDto loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {

                AppUser appUser = authRepository.findByEmail(loginDto.getEmail());
                Doctor doctor = doctorRepository.findByAppUser(appUser)
                        .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
                ;

                String token = jwtService.generateToken(appUser);

                return userMapper.toUserDto(appUser, doctor, token, "Login successful");
            }

            throw new AuthenticationException("Authentication failed");

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        } catch (DisabledException e) {
            throw new AccountDisabledException("Account is disabled");
        } catch (LockedException e) {
            throw new AccountLockedException("Account is locked");
        }
    }
}
