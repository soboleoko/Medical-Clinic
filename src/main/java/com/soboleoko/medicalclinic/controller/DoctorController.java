package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.DoctorMapper;
import com.soboleoko.medicalclinic.model.CreateDoctorDTO;
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
    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public List<DoctorDTO> getDoctors() {
        return doctorMapper.mapToDoctorDTOList(doctorService.getDoctors());
    }

    @GetMapping("/doctors/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return doctorMapper.mapToDoctorDTO(doctorService.findByEmail(email));
    }

    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO addDoctor(@RequestBody @Valid CreateDoctorDTO doctor) {
        return doctorMapper.mapToDoctorDTO(doctorService.addDoctor(doctorMapper.mapToDoctor(doctor)));
    }

    @DeleteMapping("/doctors/{email}")
    public void deleteDoctor(@PathVariable String email) {
        doctorService.deleteDoctor(email);
    }

    @PutMapping("/doctors/{email}")
    public DoctorDTO updateDoctor(@PathVariable String email, @RequestBody @Valid CreateDoctorDTO newDoctorData) {
        return doctorMapper.mapToDoctorDTO(doctorService.updateDoctor(email, doctorMapper.mapToDoctor(newDoctorData)));
    }

    @PatchMapping("doctors/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody @Valid String password) {
        doctorService.updatePassword(email, password);
    }
}
