package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.model.Patient;
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

    @GetMapping("/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)

    private Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    @PutMapping("/patients/{email}")
    public Patient updatePatient(@PathVariable String email, @RequestBody Patient newPatientData) {
        return patientService.updatePatient(email, newPatientData);
    }
    @PatchMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String email, @RequestBody String password) {
        patientService.updatePassword(email,password);
    }
}
