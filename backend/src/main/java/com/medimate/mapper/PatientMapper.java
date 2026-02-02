package com.medimate.mapper;

import com.medimate.dto.PatientRequestDto;
import com.medimate.dto.PatientResponseDto;
import com.medimate.model.Doctor;
import com.medimate.model.Patient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    public Patient toPatient(PatientRequestDto dto, Doctor doctor) {

        Patient patient = new Patient();

        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setNic(dto.getNic());
        patient.setEmail(dto.getEmail());
        patient.setDoctor(doctor);

        return patient;
    }

    public void updatePatient(Patient patient, PatientRequestDto dto) {

        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setNic(dto.getNic());
        patient.setEmail(dto.getEmail());
    }

    public void patchPatient(Patient patient, PatientRequestDto dto) {

        if (dto.getName() != null) patient.setName(dto.getName());
        if (dto.getAge() != null) patient.setAge(dto.getAge());
        if (dto.getGender() != null) patient.setGender(dto.getGender());
        if (dto.getPhone() != null) patient.setPhone(dto.getPhone());
        if (dto.getNic() != null) patient.setNic(dto.getNic());
        if (dto.getEmail() != null) patient.setEmail(dto.getEmail());
    }

    public PatientResponseDto toResponseDto(Patient patient) {

        PatientResponseDto dto = new PatientResponseDto();

        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setPhone(patient.getPhone());
        dto.setNic(patient.getNic());
        dto.setEmail(patient.getEmail());

        return dto;
    }

    public List<PatientResponseDto> toResponseDtoList(List<Patient> patients) {
        return patients.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
