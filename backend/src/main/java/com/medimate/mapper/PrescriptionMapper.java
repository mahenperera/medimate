package com.medimate.mapper;

import com.medimate.dto.PrescriptionItemResponseDto;
import com.medimate.dto.PrescriptionResponseDto;
import com.medimate.model.Prescription;
import com.medimate.model.PrescriptionItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrescriptionMapper {

    public PrescriptionItemResponseDto toItemResponseDto(PrescriptionItem item) {

        PrescriptionItemResponseDto dto = new PrescriptionItemResponseDto();

        dto.setId(item.getId());
        dto.setMedicineId(item.getMedicine().getId());
        dto.setMedicineName(item.getMedicine().getName());
        dto.setMedicineForm(item.getMedicine().getForm());
        dto.setMedicineDosage(item.getMedicine().getDosage());
        dto.setQuantity(item.getQuantity());
        dto.setDosageInstructions(item.getDosageInstructions());

        return dto;
    }

    public PrescriptionResponseDto toResponseDto(Prescription prescription) {

        PrescriptionResponseDto dto = new PrescriptionResponseDto();

        dto.setId(prescription.getId());
        dto.setPatientId(prescription.getPatient().getId());
        dto.setPatientName(prescription.getPatient().getName());
        dto.setVisitId(prescription.getVisit() != null ? prescription.getVisit().getId() : null);
        dto.setPrescriptionDate(prescription.getPrescriptionDate());
        dto.setCreatedAt(prescription.getCreatedAt());

        List<PrescriptionItemResponseDto> itemDtos = prescription.getItems().stream()
                .map(this::toItemResponseDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        return dto;
    }

    public List<PrescriptionResponseDto> toResponseDtoList(List<Prescription> prescriptions) {
        return prescriptions.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
