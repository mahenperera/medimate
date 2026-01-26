package com.medimate.repository;

import com.medimate.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID> {

    List<Medicine> findByDoctorId(UUID doctorId);

    List<Medicine> findByDoctorIdAndQuantityLessThan(UUID doctorId, Integer quantity);

    List<Medicine> findByExpiryDateBefore(LocalDate date);
}
