package com.medimate.service.impl;

import com.medimate.dto.VisitPatchDto;
import com.medimate.dto.VisitRequestDto;
import com.medimate.dto.VisitResponseDto;
import com.medimate.mapper.VisitMapper;
import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import com.medimate.model.Visit;
import com.medimate.repository.VisitRepository;
import com.medimate.service.VisitService;
import com.medimate.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final AuthUtil authUtil;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository,
                            VisitMapper visitMapper,
                            AuthUtil authUtil) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.authUtil = authUtil;
    }

    @Override
    @Transactional
    public VisitResponseDto createVisit(VisitRequestDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Patient patient = authUtil.getPatientByIdAndDoctor(dto.getPatientId(), doctor);

        Visit visit = visitMapper.toVisit(dto, patient, doctor);
        Visit savedVisit = visitRepository.save(visit);

        return visitMapper.toResponseDto(savedVisit);
    }

    @Override
    @Transactional(readOnly = true)
    public VisitResponseDto getVisit(UUID visitId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Visit visit = authUtil.getVisitByIdAndDoctor(visitId, doctor);

        return visitMapper.toResponseDto(visit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitResponseDto> getAllVisits() {

        Doctor doctor = authUtil.getLoggedInDoctor();
        List<Visit> visits = visitRepository.findByDoctorOrderByVisitDateDesc(doctor);

        return visitMapper.toResponseDtoList(visits);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitResponseDto> getVisitsByPatient(UUID patientId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Patient patient = authUtil.getPatientByIdAndDoctor(patientId, doctor);

        List<Visit> visits = visitRepository.findByPatientOrderByVisitDateDesc(patient);

        return visitMapper.toResponseDtoList(visits);
    }

    @Override
    @Transactional
    public VisitResponseDto updateVisit(UUID visitId, VisitRequestDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Visit visit = authUtil.getVisitByIdAndDoctor(visitId, doctor);

        visitMapper.updateVisit(visit, dto);
        Visit updatedVisit = visitRepository.save(visit);

        return visitMapper.toResponseDto(updatedVisit);
    }

    @Override
    @Transactional
    public VisitResponseDto patchVisit(UUID visitId, VisitPatchDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Visit visit = authUtil.getVisitByIdAndDoctor(visitId, doctor);

        visitMapper.patchVisit(visit, dto);
        Visit updatedVisit = visitRepository.save(visit);

        return visitMapper.toResponseDto(updatedVisit);
    }

    @Override
    @Transactional
    public void deleteVisit(UUID visitId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Visit visit = authUtil.getVisitByIdAndDoctor(visitId, doctor);

        visitRepository.delete(visit);
    }
}
