package com.medimate.util;

import com.medimate.exception.ResourceNotFoundException;
import com.medimate.model.Doctor;
import com.medimate.model.Medicine;
import com.medimate.model.Patient;
import com.medimate.model.Visit;
import com.medimate.repository.*;
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
    private final VisitRepository visitRepository;
    private final MedicineRepository medicineRepository;

    @Autowired
    public AuthUtil(AuthRepository authRepository,
                    DoctorRepository doctorRepository,
                    PatientRepository patientRepository,
                    VisitRepository visitRepository,
                    MedicineRepository medicineRepository) {
        this.authRepository = authRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.visitRepository = visitRepository;
        this.medicineRepository = medicineRepository;
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

    public Visit getVisitByIdAndDoctor(UUID visitId, Doctor doctor) {
        return visitRepository.findByIdAndDoctor(visitId, doctor)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found"));
    }

    public Medicine getMedicineByIdAndDoctor(UUID medicineId, Doctor doctor) {
        return medicineRepository.findByIdAndDoctor(medicineId, doctor)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
    }
}
