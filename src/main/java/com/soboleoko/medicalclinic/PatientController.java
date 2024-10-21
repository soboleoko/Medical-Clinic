package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {


        private final PatientService patientService;

        @Autowired
        public PatientController(PatientService patientService) {
            this.patientService = patientService;
        }

        @GetMapping
        public List<Patient> getAllPatients() {
            return patientService.getAllPatients();
        }

        @GetMapping("/{email}")
        public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
            return patientService.getPatientByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
            Patient newPatient = patientService.addPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
        }

        @DeleteMapping("/{email}")
        public ResponseEntity<Void> deletePatientByEmail(@PathVariable String email) {
            patientService.deletePatientByEmail(email);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{email}")
        public ResponseEntity<Patient> updatePatient(@PathVariable String email, @RequestBody Patient newPatientData) {
            try {
                Patient updatedPatient = patientService.updatePatient(email, newPatientData);
                return ResponseEntity.ok(updatedPatient);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }
    }
