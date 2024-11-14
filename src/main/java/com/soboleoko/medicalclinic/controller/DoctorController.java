package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.MedicalClinicMapper;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.DoctorDTO;
import com.soboleoko.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class DoctorController {
    private final MedicalClinicMapper mapper;
    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public List<DoctorDTO> getDoctors() {
        return mapper.mapToDoctorDTOList(doctorService.getDoctors());
    }

    @GetMapping("/doctors/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return mapper.mapToDoctorDTO(doctorService.getDoctorByEmail(email));
    }

    @PostMapping("/doctors")
    public DoctorDTO addDoctor(@RequestBody Doctor doctor) {
        return mapper.mapToDoctorDTO(doctorService.addDoctor(doctor));
    }

    @DeleteMapping("/doctors/{email}")
    public void deleteDoctor(@PathVariable String email) {
        doctorService.deleteDoctor(email);
    }

    @PutMapping("/doctors/{email}")
    public DoctorDTO updateDoctor(@PathVariable String email, @RequestBody Doctor newDoctorData) {
        return mapper.mapToDoctorDTO(doctorService.updateDoctor(email,newDoctorData));
    }
}
