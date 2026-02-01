package com.medimate.util;

import com.medimate.exception.ResourceNotFoundException;
import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import com.medimate.repository.AuthRepository;
import com.medimate.repository.DoctorRepository;
import com.medimate.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtil {

    private final AuthRepository authRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AuthUtil(AuthRepository authRepository,
                    DoctorRepository doctorRepository,
                    PatientRepository patientRepository) {
        this.authRepository = authRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public String getLoggedInEmail() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new ResourceNotFoundException("Not authenticated");
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        return userDetails.getUsername();
    }

    public Doctor getLoggedInDoctor() {

        String email = getLoggedInEmail();
        var appUser = authRepository.findByEmail(email);

        if (appUser == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return doctorRepository.findByAppUser(appUser)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    public Patient getPatientByIdAndDoctor(UUID patientId, Doctor doctor) {
        return patientRepository.findByIdAndDoctor(patientId, doctor)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }
}
