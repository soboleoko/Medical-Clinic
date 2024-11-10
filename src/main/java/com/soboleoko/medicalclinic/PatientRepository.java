package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.exception.EmailAlreadyExistsException;
import com.soboleoko.medicalclinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients = new ArrayList<>();
    private final HashSet<String> emails = new HashSet<>();

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }
    public HashSet<String> getEmails() {
        return new HashSet<String>(emails);
    }

    public Patient addPatient(Patient patient) {
        if (!emails.add(patient.getEmail())) {
            throw new EmailAlreadyExistsException("Pacjent o takim adresie już istnieje", HttpStatus.BAD_REQUEST);
        }
        patients.add(patient);
        return patient;
    }

    public Optional<Patient> update(String email, Patient newPatientData) {
        Optional<Patient> existingPatient = findByEmail(email);
        if (existingPatient.isPresent()) {
            Patient patient = existingPatient.get();
            patient.setBirthday(newPatientData.getBirthday());
            patient.setEmail(newPatientData.getEmail());
            patient.setFirstName(newPatientData.getFirstName());
            patient.setLastName(newPatientData.getLastName());
            patient.setPhoneNumber(newPatientData.getPhoneNumber());
        }
        return existingPatient;
    }

    public boolean deleteByEmail(String email) {
         emails.removeIf(removedEmail -> removedEmail.equals(email));
        return patients.removeIf(patient -> patient.getEmail().equals(email));
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    public void updatePassword(String email, String password){
        Optional<Patient> existingPatient = findByEmail(email);
        if (existingPatient.isPresent()) {
            Patient patient = existingPatient.get();
            patient.setPassword(password);
        }
    }
}
