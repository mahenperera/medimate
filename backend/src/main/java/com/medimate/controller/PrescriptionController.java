package com.medimate.controller;

import com.medimate.dto.PrescriptionPatchDto;
import com.medimate.dto.PrescriptionRequestDto;
import com.medimate.dto.PrescriptionResponseDto;
import com.medimate.dto.PrescriptionUpdateDto;
import com.medimate.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionResponseDto> createPrescription(@RequestBody PrescriptionRequestDto dto) {
        PrescriptionResponseDto prescription = prescriptionService.createPrescription(dto);
        return new ResponseEntity<>(prescription, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionResponseDto>> getAllPrescriptions() {
        List<PrescriptionResponseDto> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponseDto>> getPrescriptionsByPatient(@PathVariable UUID patientId) {
        List<PrescriptionResponseDto> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDto> getPrescription(@PathVariable UUID id) {
        PrescriptionResponseDto prescription = prescriptionService.getPrescription(id);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDto> updatePrescription(
            @PathVariable UUID id,
            @RequestBody PrescriptionUpdateDto dto) {
        PrescriptionResponseDto prescription = prescriptionService.updatePrescription(id, dto);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDto> patchPrescription(
            @PathVariable UUID id,
            @RequestBody PrescriptionPatchDto dto) {
        PrescriptionResponseDto prescription = prescriptionService.patchPrescription(id, dto);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable UUID id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
