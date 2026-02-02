package com.medimate.controller;

import com.medimate.dto.VisitPatchDto;
import com.medimate.dto.VisitRequestDto;
import com.medimate.dto.VisitResponseDto;
import com.medimate.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<VisitResponseDto> createVisit(@RequestBody VisitRequestDto dto) {
        VisitResponseDto visit = visitService.createVisit(dto);
        return new ResponseEntity<>(visit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VisitResponseDto>> getAllVisits() {
        List<VisitResponseDto> visits = visitService.getAllVisits();
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitResponseDto>> getVisitsByPatient(@PathVariable UUID patientId) {
        List<VisitResponseDto> visits = visitService.getVisitsByPatient(patientId);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitResponseDto> getVisit(@PathVariable UUID id) {
        VisitResponseDto visit = visitService.getVisit(id);
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitResponseDto> updateVisit(@PathVariable UUID id,
                                                        @RequestBody VisitRequestDto dto) {
        VisitResponseDto visit = visitService.updateVisit(id, dto);
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VisitResponseDto> patchVisit(@PathVariable UUID id,
                                                       @RequestBody VisitPatchDto dto) {
        VisitResponseDto visit = visitService.patchVisit(id, dto);
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable UUID id) {
        visitService.deleteVisit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
