package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.service.PatientService;
import com.soboleoko.medicalclinic.mapper.MedicalClinicMapper;
import com.soboleoko.medicalclinic.model.Patient;
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
    private final MedicalClinicMapper patientMapper;

    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients() {
        return patientMapper.mapToPatientDTOList(patientService.getAllPatients());
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.mapToPatientDTO(patientService.getPatientByEmail(email));
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    private PatientDTO addPatient(@RequestBody @Valid Patient patient) {
        return patientMapper.mapToPatientDTO(patientService.addPatient(patient));
    }

    @DeleteMapping("/patients/{email}")
    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody @Valid Patient newPatientData) {
        return patientMapper.mapToPatientDTO(patientService.updatePatient(email, newPatientData));
    }

    @PatchMapping("/patients/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody @Valid String password) {
        patientService.updatePassword(email, password);
    }
}
