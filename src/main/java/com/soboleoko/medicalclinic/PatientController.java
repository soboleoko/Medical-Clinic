package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.mapper.PatientMapper;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        PatientMapper patientMapper = new PatientMapper();
        return patientMapper.patientToDTO(patientService.getPatientByEmail(email));
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    private PatientDTO addPatient(@RequestBody Patient patient) {
        PatientMapper patientMapper = new PatientMapper();
        return patientMapper.patientToDTO(patientService.addPatient(patient));
    }

    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody Patient newPatientData) {
        PatientMapper patientMapper = new PatientMapper();
        return patientMapper.patientToDTO(patientService.updatePatient(email,newPatientData));
    }
    @PatchMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String email, @RequestBody String password) {
        patientService.updatePassword(email,password);
    }
}
