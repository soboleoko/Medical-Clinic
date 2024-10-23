package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with given email does not exist."));
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);    }

    public void deletePatientByEmail(String email) {
        patientRepository.deleteByEmail(email);
    }

    public Patient updatePatient(String email, Patient newPatientData) {
        patientRepository.update(email, newPatientData);
        return newPatientData;
    }
}
