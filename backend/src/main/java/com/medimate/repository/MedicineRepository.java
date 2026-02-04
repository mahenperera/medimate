package com.medimate.repository;

import com.medimate.model.Doctor;
import com.medimate.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID> {

    List<Medicine> findByDoctorOrderByNameAsc(Doctor doctor);

    Optional<Medicine> findByIdAndDoctor(UUID id, Doctor doctor);

    Optional<Medicine> findByNameAndDoctor(String name, Doctor doctor);
}
