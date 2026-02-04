package com.medimate.service.impl;

import com.medimate.dto.MedicinePatchDto;
import com.medimate.dto.MedicineRequestDto;
import com.medimate.dto.MedicineResponseDto;
import com.medimate.mapper.MedicineMapper;
import com.medimate.model.Doctor;
import com.medimate.model.Medicine;
import com.medimate.repository.MedicineRepository;
import com.medimate.service.MedicineService;
import com.medimate.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;
    private final AuthUtil authUtil;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository,
                               MedicineMapper medicineMapper,
                               AuthUtil authUtil) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
        this.authUtil = authUtil;
    }

    @Override
    @Transactional
    public MedicineResponseDto createMedicine(MedicineRequestDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();

        Medicine medicine = medicineMapper.toMedicine(dto, doctor);
        Medicine savedMedicine = medicineRepository.save(medicine);

        return medicineMapper.toResponseDto(savedMedicine);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineResponseDto getMedicine(UUID medicineId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Medicine medicine = authUtil.getMedicineByIdAndDoctor(medicineId, doctor);

        return medicineMapper.toResponseDto(medicine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineResponseDto> getAllMedicines() {

        Doctor doctor = authUtil.getLoggedInDoctor();
        List<Medicine> medicines = medicineRepository.findByDoctorOrderByNameAsc(doctor);

        return medicineMapper.toResponseDtoList(medicines);
    }

    @Override
    @Transactional
    public MedicineResponseDto updateMedicine(UUID medicineId, MedicineRequestDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Medicine medicine = authUtil.getMedicineByIdAndDoctor(medicineId, doctor);

        medicineMapper.updateMedicine(medicine, dto);
        Medicine updatedMedicine = medicineRepository.save(medicine);

        return medicineMapper.toResponseDto(updatedMedicine);
    }

    @Override
    @Transactional
    public MedicineResponseDto patchMedicine(UUID medicineId, MedicinePatchDto dto) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Medicine medicine = authUtil.getMedicineByIdAndDoctor(medicineId, doctor);

        medicineMapper.patchMedicine(medicine, dto);
        Medicine updatedMedicine = medicineRepository.save(medicine);

        return medicineMapper.toResponseDto(updatedMedicine);
    }

    @Override
    @Transactional
    public void deleteMedicine(UUID medicineId) {

        Doctor doctor = authUtil.getLoggedInDoctor();
        Medicine medicine = authUtil.getMedicineByIdAndDoctor(medicineId, doctor);

        medicineRepository.delete(medicine);
    }
}
