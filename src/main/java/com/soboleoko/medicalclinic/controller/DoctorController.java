package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.DoctorMapper;
import com.soboleoko.medicalclinic.model.*;
import com.soboleoko.medicalclinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/doctors")
@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getDoctors() {
        return doctorMapper.mapToDoctorDTOList(doctorService.getDoctors());
    }

    @GetMapping("/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return doctorMapper.mapToDoctorDTO(doctorService.findByEmail(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO addDoctor(@RequestBody @Valid CreateDoctorDTO doctor) {
        return doctorMapper.mapToDoctorDTO(doctorService.addDoctor(doctorMapper.mapToDoctor(doctor)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deleteDoctor(@PathVariable String email) {
        doctorService.deleteDoctor(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO updateDoctor(@PathVariable String email, @RequestBody @Valid CreateDoctorDTO newDoctorData) {
        return doctorMapper.mapToDoctorDTO(doctorService.updateDoctor(email, doctorMapper.mapToDoctor(newDoctorData)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody @Valid UpdatePasswordDTO password) {
        doctorService.updatePassword(email, password);
    }
}