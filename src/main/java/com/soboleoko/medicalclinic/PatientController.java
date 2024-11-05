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
    private final PatientMapper patientMapper;

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
    private PatientDTO addPatient(@RequestBody Patient patient) {
        return patientMapper.mapToPatientDTO(patientService.addPatient(patient));
    }

    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody Patient newPatientData) {
        return patientMapper.mapToPatientDTO(patientService.updatePatient(email, newPatientData));
    }

    @PatchMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String email, @RequestBody String password) {
        patientService.updatePassword(email, password);
    }
}
