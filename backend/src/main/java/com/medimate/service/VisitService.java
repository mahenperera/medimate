package com.medimate.service;

import com.medimate.dto.VisitRequestDto;
import com.medimate.dto.VisitResponseDto;
import com.medimate.dto.VisitPatchDto;

import java.util.List;
import java.util.UUID;

public interface VisitService {

    VisitResponseDto createVisit(VisitRequestDto dto);

    VisitResponseDto getVisit(UUID visitId);

    List<VisitResponseDto> getAllVisits();

    List<VisitResponseDto> getVisitsByPatient(UUID patientId);

    VisitResponseDto updateVisit(UUID visitId, VisitRequestDto dto);

    VisitResponseDto patchVisit(UUID visitId, VisitPatchDto dto);

    void deleteVisit(UUID visitId);
}
