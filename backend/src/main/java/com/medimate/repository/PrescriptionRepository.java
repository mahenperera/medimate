package com.medimate.repository;

import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import com.medimate.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    List<Prescription> findByDoctorOrderByPrescriptionDateDesc(Doctor doctor);

    List<Prescription> findByPatientOrderByPrescriptionDateDesc(Patient patient);

    Optional<Prescription> findByIdAndDoctor(UUID id, Doctor doctor);
}
