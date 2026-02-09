package com.medimate.service.impl;

import com.medimate.dto.*;
import com.medimate.exception.InsufficientStockException;
import com.medimate.mapper.PrescriptionMapper;
import com.medimate.model.*;
import com.medimate.repository.MedicineRepository;
import com.medimate.repository.PrescriptionRepository;
import com.medimate.service.PrescriptionService;
import com.medimate.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final AuthUtil authUtil;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   MedicineRepository medicineRepository,
                                   PrescriptionMapper prescriptionMapper,
                                   AuthUtil authUtil) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicineRepository = medicineRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.authUtil = authUtil;
    }

    @Override
    @Transactional
    public PrescriptionResponseDto createPrescription(PrescriptionRequestDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Patient patient = authUtil.getPatientByIdAndDoctor(dto.getPatientId(), doctor);

        Visit visit = null;
        if (dto.getVisitId() != null) {
            visit = authUtil.getVisitByIdAndDoctor(dto.getVisitId(), doctor);
        }

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setVisit(visit);
        prescription.setPrescriptionDate(dto.getPrescriptionDate());

        List<PrescriptionItem> items = new ArrayList<>();
        for (PrescriptionItemRequestDto itemDto : dto.getItems()) {
            Medicine medicine = authUtil.getMedicineByIdAndDoctor(itemDto.getMedicineId(), doctor);

            if (medicine.getQuantity() < itemDto.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for " + medicine.getName() +
                                " (" + medicine.getForm() + ", " + medicine.getDosage() + "). " +
                                "Available: " + medicine.getQuantity() + ", Required: " + itemDto.getQuantity()
                );
            }

            medicine.setQuantity(medicine.getQuantity() - itemDto.getQuantity());
            medicineRepository.save(medicine);

            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(prescription);
            item.setMedicine(medicine);
            item.setQuantity(itemDto.getQuantity());
            item.setDosageInstructions(itemDto.getDosageInstructions());

            items.add(item);
        }

        prescription.setItems(items);
        Prescription savedPrescription = prescriptionRepository.save(prescription);

        return prescriptionMapper.toResponseDto(savedPrescription);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponseDto getPrescription(UUID prescriptionId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Prescription prescription = authUtil.getPrescriptionByIdAndDoctor(prescriptionId, doctor);

        return prescriptionMapper.toResponseDto(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponseDto> getAllPrescriptions() {

        Doctor doctor = authUtil.getLoggedInDoctor();
        List<Prescription> prescriptions = prescriptionRepository.findByDoctorOrderByPrescriptionDateDesc(doctor);

        return prescriptionMapper.toResponseDtoList(prescriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponseDto> getPrescriptionsByPatient(UUID patientId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Patient patient = authUtil.getPatientByIdAndDoctor(patientId, doctor);
        List<Prescription> prescriptions = prescriptionRepository.findByPatientOrderByPrescriptionDateDesc(patient);

        return prescriptionMapper.toResponseDtoList(prescriptions);
    }

    @Override
    @Transactional
    public PrescriptionResponseDto updatePrescription(UUID prescriptionId, PrescriptionUpdateDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Prescription prescription = authUtil.getPrescriptionByIdAndDoctor(prescriptionId, doctor);

        Patient patient = authUtil.getPatientByIdAndDoctor(dto.getPatientId(), doctor);

        Visit visit = null;
        if (dto.getVisitId() != null) {
            visit = authUtil.getVisitByIdAndDoctor(dto.getVisitId(), doctor);
        }

        for (PrescriptionItem oldItem : prescription.getItems()) {
            Medicine medicine = oldItem.getMedicine();
            medicine.setQuantity(medicine.getQuantity() + oldItem.getQuantity());
            medicineRepository.save(medicine);
        }

        prescription.getItems().clear();

        prescription.setPatient(patient);
        prescription.setVisit(visit);
        prescription.setPrescriptionDate(dto.getPrescriptionDate());

        List<PrescriptionItem> newItems = new ArrayList<>();
        for (PrescriptionItemRequestDto itemDto : dto.getItems()) {
            Medicine medicine = authUtil.getMedicineByIdAndDoctor(itemDto.getMedicineId(), doctor);

            if (medicine.getQuantity() < itemDto.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for " + medicine.getName() +
                                " (" + medicine.getForm() + ", " + medicine.getDosage() + "). " +
                                "Available: " + medicine.getQuantity() + ", Required: " + itemDto.getQuantity()
                );
            }

            medicine.setQuantity(medicine.getQuantity() - itemDto.getQuantity());
            medicineRepository.save(medicine);

            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(prescription);
            item.setMedicine(medicine);
            item.setQuantity(itemDto.getQuantity());
            item.setDosageInstructions(itemDto.getDosageInstructions());

            newItems.add(item);
        }

        prescription.setItems(newItems);
        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        return prescriptionMapper.toResponseDto(updatedPrescription);
    }

    @Override
    @Transactional
    public PrescriptionResponseDto patchPrescription(UUID prescriptionId, PrescriptionPatchDto dto) {
        Doctor doctor = authUtil.getLoggedInDoctor();
        Prescription prescription = authUtil.getPrescriptionByIdAndDoctor(prescriptionId, doctor);

        if (dto.getPatientId() != null) {
            Patient patient = authUtil.getPatientByIdAndDoctor(dto.getPatientId(), doctor);
            prescription.setPatient(patient);
        }

        if (dto.getVisitId() != null) {
            Visit visit = authUtil.getVisitByIdAndDoctor(dto.getVisitId(), doctor);
            prescription.setVisit(visit);
        }

        if (dto.getPrescriptionDate() != null) {
            prescription.setPrescriptionDate(dto.getPrescriptionDate());
        }

        if (dto.getItems() != null) {
            for (PrescriptionItem oldItem : prescription.getItems()) {
                Medicine medicine = oldItem.getMedicine();
                medicine.setQuantity(medicine.getQuantity() + oldItem.getQuantity());
                medicineRepository.save(medicine);
            }

            prescription.getItems().clear();

            List<PrescriptionItem> newItems = new ArrayList<>();
            for (PrescriptionItemRequestDto itemDto : dto.getItems()) {
                Medicine medicine = authUtil.getMedicineByIdAndDoctor(itemDto.getMedicineId(), doctor);

                if (medicine.getQuantity() < itemDto.getQuantity()) {
                    throw new InsufficientStockException(
                            "Insufficient stock for " + medicine.getName() +
                                    " (" + medicine.getForm() + ", " + medicine.getDosage() + "). " +
                                    "Available: " + medicine.getQuantity() + ", Required: " + itemDto.getQuantity()
                    );
                }

                medicine.setQuantity(medicine.getQuantity() - itemDto.getQuantity());
                medicineRepository.save(medicine);

                PrescriptionItem item = new PrescriptionItem();
                item.setPrescription(prescription);
                item.setMedicine(medicine);
                item.setQuantity(itemDto.getQuantity());
                item.setDosageInstructions(itemDto.getDosageInstructions());

                newItems.add(item);
            }

            prescription.setItems(newItems);
        }

        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponseDto(updatedPrescription);
    }

    @Override
    @Transactional
    public void deletePrescription(UUID prescriptionId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Prescription prescription = authUtil.getPrescriptionByIdAndDoctor(prescriptionId, doctor);

        for (PrescriptionItem item : prescription.getItems()) {
            Medicine medicine = item.getMedicine();
            medicine.setQuantity(medicine.getQuantity() + item.getQuantity());
            medicineRepository.save(medicine);
        }

        prescriptionRepository.delete(prescription);
    }
}
