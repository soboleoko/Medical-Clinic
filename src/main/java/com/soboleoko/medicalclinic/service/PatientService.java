package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.PatientAlreadyExistsException;
import com.soboleoko.medicalclinic.exception.PatientNotFoundException;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient with given email does not exist."));
    }

    public Patient addPatient(Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findByEmail(patient.getEmail());
        if (existingPatient.isPresent()) {
            throw new PatientAlreadyExistsException("Email is already in use", HttpStatus.BAD_REQUEST);
        }
        return patientRepository.addPatient(patient);
    }

    public boolean deletePatientByEmail(String email) {
        return patientRepository.deleteByEmail(email);
    }

    public Patient updatePatient(String email, Patient newPatientData) {
        return patientRepository.update(email, newPatientData)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient with given email does not exist."));
    }

    public void updatePassword(String email, String password) {
        patientRepository.updatePassword(email, password);
    }
}