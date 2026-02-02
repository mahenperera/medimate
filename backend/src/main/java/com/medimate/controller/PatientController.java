package com.medimate.controller;

import com.medimate.dto.PatientRequestDto;
import com.medimate.dto.PatientResponseDto;
import com.medimate.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody PatientRequestDto dto) {
        PatientResponseDto patient = patientService.createPatient(dto);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatient(@PathVariable UUID id) {
        PatientResponseDto patient = patientService.getPatient(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id,
                                                            @RequestBody PatientRequestDto dto) {
        PatientResponseDto patient = patientService.updatePatient(id, dto);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDto> patchPatient(@PathVariable UUID id,
                                                           @RequestBody PatientRequestDto dto) {
        PatientResponseDto patient = patientService.patchPatient(id, dto);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
