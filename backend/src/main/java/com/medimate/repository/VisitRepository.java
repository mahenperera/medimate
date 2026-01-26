package com.medimate.repository;

import com.medimate.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {

    List<Visit> findByDoctorId(UUID doctorId);

    List<Visit> findByPatientId(UUID patientId);
}
