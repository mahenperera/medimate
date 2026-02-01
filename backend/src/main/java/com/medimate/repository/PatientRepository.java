package com.medimate.repository;

import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findByDoctor(Doctor doctor);

    Optional<Patient> findByIdAndDoctor(UUID id, Doctor doctor);

    boolean existsByNicAndDoctor(String nic, Doctor doctor);
}
