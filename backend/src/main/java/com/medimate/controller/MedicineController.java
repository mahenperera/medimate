package com.medimate.controller;

import com.medimate.dto.MedicinePatchDto;
import com.medimate.dto.MedicineRequestDto;
import com.medimate.dto.MedicineResponseDto;
import com.medimate.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public ResponseEntity<MedicineResponseDto> createMedicine(@RequestBody MedicineRequestDto dto) {
        MedicineResponseDto medicine = medicineService.createMedicine(dto);
        return new ResponseEntity<>(medicine, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicineResponseDto>> getAllMedicines() {
        List<MedicineResponseDto> medicines = medicineService.getAllMedicines();
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> getMedicine(@PathVariable UUID id) {
        MedicineResponseDto medicine = medicineService.getMedicine(id);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> updateMedicine(@PathVariable UUID id,
                                                              @RequestBody MedicineRequestDto dto) {
        MedicineResponseDto medicine = medicineService.updateMedicine(id, dto);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> patchMedicine(@PathVariable UUID id,
                                                             @RequestBody MedicinePatchDto dto) {
        MedicineResponseDto medicine = medicineService.patchMedicine(id, dto);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable UUID id) {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
