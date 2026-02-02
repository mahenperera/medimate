package com.medimate.service;

import com.medimate.dto.PatientRequestDto;
import com.medimate.dto.PatientResponseDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientResponseDto createPatient(PatientRequestDto dto);

    PatientResponseDto getPatient(UUID patientId);

    List<PatientResponseDto> getAllPatients();

    PatientResponseDto updatePatient(UUID patientId, PatientRequestDto dto);

    PatientResponseDto patchPatient(UUID patientId, PatientRequestDto dto);

    void deletePatient(UUID patientId);
}
