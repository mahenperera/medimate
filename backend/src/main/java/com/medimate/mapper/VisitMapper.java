package com.medimate.mapper;

import com.medimate.dto.VisitRequestDto;
import com.medimate.dto.VisitResponseDto;
import com.medimate.dto.VisitPatchDto;
import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import com.medimate.model.Visit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    public Visit toVisit(VisitRequestDto dto, Patient patient, Doctor doctor) {

        Visit visit = new Visit();

        visit.setVisitDate(dto.getVisitDate());
        visit.setDiagnosis(dto.getDiagnosis());
        visit.setNotes(dto.getNotes());
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        return visit;
    }

    public void updateVisit(Visit visit, VisitRequestDto dto) {

        visit.setVisitDate(dto.getVisitDate());
        visit.setDiagnosis(dto.getDiagnosis());
        visit.setNotes(dto.getNotes());
    }

    public void patchVisit(Visit visit, VisitPatchDto dto) {

        if (dto.getVisitDate() != null) visit.setVisitDate(dto.getVisitDate());
        if (dto.getDiagnosis() != null) visit.setDiagnosis(dto.getDiagnosis());
        if (dto.getNotes() != null) visit.setNotes(dto.getNotes());
    }

    public VisitResponseDto toResponseDto(Visit visit) {

        VisitResponseDto dto = new VisitResponseDto();

        dto.setId(visit.getId());
        dto.setPatientId(visit.getPatient().getId());
        dto.setPatientName(visit.getPatient().getName());
        dto.setVisitDate(visit.getVisitDate());
        dto.setDiagnosis(visit.getDiagnosis());
        dto.setNotes(visit.getNotes());
        dto.setCreatedAt(visit.getCreatedAt());

        return dto;
    }

    public List<VisitResponseDto> toResponseDtoList(List<Visit> visits) {
        return visits.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
