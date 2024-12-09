package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.PatientAlreadyExistsException;
import com.soboleoko.medicalclinic.exception.PatientNotFoundException;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient addPatient(Patient patient) {
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new PatientAlreadyExistsException("Provided email is in use", HttpStatus.BAD_REQUEST);
        }
        return patientRepository.save(patient);
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(String email, Patient newPatientData) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
        existingPatient.setBirthday(newPatientData.getBirthday());
        existingPatient.setEmail(newPatientData.getEmail());
        existingPatient.setFirstName(newPatientData.getFirstName());
        existingPatient.setLastName(newPatientData.getLastName());
        existingPatient.setPhoneNumber(newPatientData.getPhoneNumber());
        patientRepository.save(existingPatient);
        return existingPatient;
    }

    public void updatePassword(String email, UpdatePasswordDTO password) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
        existingPatient.setPassword(password.getPassword());
        patientRepository.save(existingPatient);
    }

    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
    }

    public void deletePatient(String email) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
        patientRepository.delete(existingPatient);
    }
}

