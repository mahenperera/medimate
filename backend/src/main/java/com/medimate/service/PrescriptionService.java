package com.medimate.service;

import com.medimate.dto.PrescriptionPatchDto;
import com.medimate.dto.PrescriptionRequestDto;
import com.medimate.dto.PrescriptionResponseDto;
import com.medimate.dto.PrescriptionUpdateDto;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {

    PrescriptionResponseDto createPrescription(PrescriptionRequestDto dto);

    PrescriptionResponseDto getPrescription(UUID prescriptionId);

    List<PrescriptionResponseDto> getAllPrescriptions();

    List<PrescriptionResponseDto> getPrescriptionsByPatient(UUID patientId);

    PrescriptionResponseDto updatePrescription(UUID prescriptionId, PrescriptionUpdateDto dto);

    PrescriptionResponseDto patchPrescription(UUID prescriptionId, PrescriptionPatchDto dto);

    void deletePrescription(UUID prescriptionId);
}
