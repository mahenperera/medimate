package com.medimate.repository;

import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import com.medimate.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {

    List<Visit> findByDoctorOrderByVisitDateDesc(Doctor doctor);

    List<Visit> findByPatientOrderByVisitDateDesc(Patient patient);

    Optional<Visit> findByIdAndDoctor(UUID id, Doctor doctor);
}
