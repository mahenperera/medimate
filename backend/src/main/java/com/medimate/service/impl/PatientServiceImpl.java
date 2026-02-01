package com.medimate.service.impl;

import com.medimate.dto.PatientRequestDto;
import com.medimate.dto.PatientResponseDto;
import com.medimate.exception.DuplicateResourceException;
import com.medimate.mapper.PatientMapper;
import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import com.medimate.repository.PatientRepository;
import com.medimate.service.PatientService;
import com.medimate.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AuthUtil authUtil;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository,
                              PatientMapper patientMapper,
                              AuthUtil authUtil) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.authUtil = authUtil;
    }

    @Override
    @Transactional
    public PatientResponseDto createPatient(PatientRequestDto dto) {
        Doctor doctor = authUtil.getLoggedInDoctor();

        if (patientRepository.existsByNicAndDoctor(dto.getNic(), doctor)) {
            throw new DuplicateResourceException("Patient with this NIC already exists");
        }

        Patient patient = patientMapper.toPatient(dto, doctor);
        Patient savedPatient = patientRepository.save(patient);

        return patientMapper.toResponseDto(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDto getPatient(UUID patientId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Patient patient = authUtil.getPatientByIdAndDoctor(patientId, doctor);

        return patientMapper.toResponseDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDto> getAllPatients() {

        Doctor doctor = authUtil.getLoggedInDoctor();
        List<Patient> patients = patientRepository.findByDoctor(doctor);

        return patientMapper.toResponseDtoList(patients);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePatient(UUID patientId, PatientRequestDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Patient patient = authUtil.getPatientByIdAndDoctor(patientId, doctor);

        if (!patient.getNic().equals(dto.getNic()) &&
                patientRepository.existsByNicAndDoctor(dto.getNic(), doctor)) {
            throw new DuplicateResourceException("Patient with this NIC already exists");
        }

        patientMapper.partialUpdatePatient(patient, dto);
        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.toResponseDto(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(UUID patientId) {

        Doctor doctor = authUtil.getLoggedInDoctor();

        Patient patient = authUtil.getPatientByIdAndDoctor(patientId, doctor);
        patientRepository.delete(patient);
    }
}
