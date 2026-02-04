package com.medimate.service;

import com.medimate.dto.MedicinePatchDto;
import com.medimate.dto.MedicineRequestDto;
import com.medimate.dto.MedicineResponseDto;

import java.util.List;
import java.util.UUID;

public interface MedicineService {

    MedicineResponseDto createMedicine(MedicineRequestDto dto);

    MedicineResponseDto getMedicine(UUID medicineId);

    List<MedicineResponseDto> getAllMedicines();

    MedicineResponseDto updateMedicine(UUID medicineId, MedicineRequestDto dto);

    MedicineResponseDto patchMedicine(UUID medicineId, MedicinePatchDto dto);

    void deleteMedicine(UUID medicineId);
}
