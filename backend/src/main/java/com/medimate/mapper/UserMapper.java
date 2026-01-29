package com.medimate.mapper;

import com.medimate.dto.UserDto;
import com.medimate.model.AppUser;
import com.medimate.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto(AppUser appUser, Doctor doctor, String token, String message) {

        UserDto userDto = new UserDto();
        userDto.setFullName(doctor.getFullName());
        userDto.setEmail(appUser.getEmail());
        userDto.setSpecialization(doctor.getSpecialization());
        userDto.setRole(appUser.getRole());
        userDto.setToken(token);
        userDto.setMessage(message);

        return userDto;
    }
}
