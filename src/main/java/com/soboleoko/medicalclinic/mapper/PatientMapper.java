package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.PatientDTO;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public final class PatientMapper {
    public static PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(patient.getEmail(), patient.getIdCardNo(), patient.getFirstName(),
                patient.getLastName(), patient.getPhoneNumber(), patient.getBirthday());
    }

    public static Patient DTOToPatient (PatientDTO patientDTO) {
        return new Patient(patientDTO.getEmail(),null, patientDTO.getIdCardNo(), patientDTO.getFirstName(),
                patientDTO.getLastName(), patientDTO.getPhoneNumber(), patientDTO.getBirthday());
    }
    public static List<PatientDTO> patientToDTOList (List<Patient> patients) {
        return patients.stream()
                .map(PatientMapper::patientToDTO)
                .toList();
    }
}