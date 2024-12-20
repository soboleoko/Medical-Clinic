package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.PatientMapper;
import com.soboleoko.medicalclinic.model.CreatePatientDTO;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.service.PatientService;
import com.soboleoko.medicalclinic.model.PatientDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients() {
        return patientMapper.mapToPatientDTOList(patientService.getPatients());
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.mapToPatientDTO(patientService.findByEmail(email));
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    private PatientDTO addPatient(@RequestBody @Valid CreatePatientDTO patient) {
        return patientMapper.mapToPatientDTO(patientService.addPatient(patientMapper.mapToPatient(patient)));
    }

    @DeleteMapping("/patients/{email}")
    public void deletePatientByEmail(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody @Valid CreatePatientDTO newPatientData) {
        return patientMapper.mapToPatientDTO(patientService.updatePatient(email, patientMapper.mapToPatient(newPatientData)));
    }

    @PatchMapping("/patients/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody @Valid UpdatePasswordDTO password) {
        patientService.updatePassword(email, password);
    }
}