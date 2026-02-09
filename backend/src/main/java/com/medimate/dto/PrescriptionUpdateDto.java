package com.medimate.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PrescriptionUpdateDto {

    private UUID patientId;
    private UUID visitId;
    private LocalDateTime prescriptionDate;
    private List<PrescriptionItemRequestDto> items;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getVisitId() {
        return visitId;
    }

    public void setVisitId(UUID visitId) {
        this.visitId = visitId;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public List<PrescriptionItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItemRequestDto> items) {
        this.items = items;
    }
}
