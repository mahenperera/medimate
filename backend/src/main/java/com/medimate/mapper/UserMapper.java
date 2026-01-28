package com.medimate.mapper;

import com.medimate.dto.UserDto;
import com.medimate.model.AppUser;
import com.medimate.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto(AppUser user, Doctor doctor) {
        UserDto dto = new UserDto();
        dto.setFullName(doctor.getFullName());
        dto.setEmail(user.getEmail());
        dto.setSpecialization(doctor.getSpecialization());

        return dto;
    }
}
