package com.medimate.repository;

import com.medimate.model.AppUser;
import com.medimate.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findByAppUser(AppUser appUser);
}
