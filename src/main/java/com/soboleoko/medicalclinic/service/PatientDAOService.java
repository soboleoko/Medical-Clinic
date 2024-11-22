package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.PatientAlreadyExistsException;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.repository.PatientDAORepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientDAOService {

    private PatientDAORepository patientDAORepository;

    public Patient addPatient(Patient patient) {
        Optional<Patient> existingPatient = patientDAORepository.findByIdCardNumber(patient.getIdCardNo());
        if (existingPatient.isPresent()) {
            throw new PatientAlreadyExistsException("Patient with given card number already exist", HttpStatus.BAD_REQUEST);
        }
        return patientDAORepository.save(patient);
    }

    public List<Patient> getPatients() {
        return patientDAORepository.findAll();
    }

    public Optional<Patient> updatePatient(String idCardNumber, Patient newPatientData) {
        Optional<Patient> existingPatient = patientDAORepository.findByIdCardNumber(idCardNumber);
        if (existingPatient.isPresent()) {
            Patient patient = existingPatient.get();
            patient.setBirthday(newPatientData.getBirthday());
            patient.setEmail(newPatientData.getEmail());
            patient.setFirstName(newPatientData.getFirstName());
            patient.setLastName(newPatientData.getLastName());
            patient.setPhoneNumber(newPatientData.getPhoneNumber());

            return Optional.of(patientDAORepository.save(patient));
        }
        return existingPatient;
    }
}
