package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void update(String email, Patient newPatientData) {
        Optional<Patient> existingPatient = findByEmail(email);
        if (existingPatient.isPresent()) {
            Patient patient = existingPatient.get();
            patient.setBirthday(newPatientData.getBirthday());
            patient.setEmail(newPatientData.getEmail());
            patient.setFirstName(newPatientData.getFirstName());
            patient.setLastName(newPatientData.getLastName());
            patient.setPhoneNumber(newPatientData.getPhoneNumber());
            patient.setIdCardNo(newPatientData.getIdCardNo());
        }
    }

    public void deleteByEmail(String email) {
        patients.removeIf(patient -> patient.getEmail().equals(email));
    }

    public Patient save(Patient patient) {
        addPatient(patient);
        return patient;
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

}
