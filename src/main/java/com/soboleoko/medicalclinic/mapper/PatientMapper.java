package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.PatientDTO;

public class PatientMapper {
    public PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(patient.getEmail(), patient.getIdCardNo(), patient.getFirstName(),
                patient.getLastName(), patient.getPhoneNumber(), patient.getBirthday());
    }

    public Patient DTOToPatient (PatientDTO patientDTO) {
        return new Patient(patientDTO.getEmail(),"", patientDTO.getIdCardNo(), patientDTO.getFirstName(),
                patientDTO.getLastName(), patientDTO.getPhoneNumber(), patientDTO.getBirthday());
    }
}