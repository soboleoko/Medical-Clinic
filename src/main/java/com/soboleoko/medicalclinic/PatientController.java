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
    public List<PatientDTO> getAllPatients() {
        return PatientMapper.patientToDTOList(patientService.getAllPatients());
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return PatientMapper.patientToDTO(patientService.getPatientByEmail(email));
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    private PatientDTO addPatient(@RequestBody Patient patient) {
        return PatientMapper.patientToDTO(patientService.addPatient(patient));
    }

    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody Patient newPatientData) {
        return PatientMapper.patientToDTO(patientService.updatePatient(email, newPatientData));
    }

    @PatchMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String email, @RequestBody String password) {
        patientService.updatePassword(email, password);
    }
}
