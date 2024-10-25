package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;


    public List<Patient> getAllPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with given email does not exist."));
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);    }

    public boolean deletePatientByEmail(String email) {
       return patientRepository.deleteByEmail(email);
    }

    public Patient updatePatient(String email, Patient newPatientData) {
        return patientRepository.update(email, newPatientData).orElseThrow(() ->  new IllegalArgumentException("Patient with given email doesnt exist"));
    }

    public void updatePassword (String email, String password) {
        patientRepository.updatePassword(email, password);
    }
}
