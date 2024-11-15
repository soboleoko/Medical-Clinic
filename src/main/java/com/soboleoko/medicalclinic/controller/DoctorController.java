package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.DoctorMapper;
import com.soboleoko.medicalclinic.mapper.PatientMapper;
import com.soboleoko.medicalclinic.model.CreateDoctorDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.DoctorDTO;
import com.soboleoko.medicalclinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public List<DoctorDTO> getDoctors() {
        return doctorMapper.mapToDoctorDTOList(doctorService.getDoctors());
    }
    @GetMapping("/doctors/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return doctorMapper.mapToDoctorDTO(doctorService.getDoctorByEmail(email));
    }

    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateDoctorDTO addDoctor(@RequestBody @Valid Doctor doctor) {
        return doctorMapper.mapToCreateDoctorDTO(doctorService.addDoctor(doctor));
    }

    @DeleteMapping("/doctors/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteDoctor(@PathVariable String email) {
        doctorService.deleteDoctor(email);
    }

    @PutMapping("/doctors/{email}")
    public CreateDoctorDTO updateDoctor(@PathVariable String email, @RequestBody @Valid Doctor newDoctorData) {
        return doctorMapper.mapToCreateDoctorDTO(doctorService.updateDoctor(email, newDoctorData));
    }
}
