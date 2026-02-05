package com.medimate.mapper;

import com.medimate.dto.MedicinePatchDto;
import com.medimate.dto.MedicineRequestDto;
import com.medimate.dto.MedicineResponseDto;
import com.medimate.model.Doctor;
import com.medimate.model.Medicine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicineMapper {

    public Medicine toMedicine(MedicineRequestDto dto, Doctor doctor) {

        Medicine medicine = new Medicine();

        medicine.setName(dto.getName());
        medicine.setForm(dto.getForm());
        medicine.setDosage(dto.getDosage());
        medicine.setQuantity(dto.getQuantity());
        medicine.setDoctor(doctor);

        return medicine;
    }

    public void updateMedicine(Medicine medicine, MedicineRequestDto dto) {

        medicine.setName(dto.getName());
        medicine.setForm(dto.getForm());
        medicine.setDosage(dto.getDosage());
        medicine.setQuantity(dto.getQuantity());
    }

    public void patchMedicine(Medicine medicine, MedicinePatchDto dto) {

        if (dto.getName() != null) medicine.setName(dto.getName());
        if (dto.getForm() != null) medicine.setForm(dto.getForm());
        if (dto.getDosage() != null) medicine.setDosage(dto.getDosage());
        if (dto.getQuantity() != null) medicine.setQuantity(dto.getQuantity());
    }

    public MedicineResponseDto toResponseDto(Medicine medicine) {
        MedicineResponseDto dto = new MedicineResponseDto();

        dto.setId(medicine.getId());
        dto.setName(medicine.getName());
        dto.setForm(medicine.getForm());
        dto.setDosage(medicine.getDosage());
        dto.setQuantity(medicine.getQuantity());
        dto.setCreatedAt(medicine.getCreatedAt());

        return dto;
    }

    public List<MedicineResponseDto> toResponseDtoList(List<Medicine> medicines) {
        return medicines.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
