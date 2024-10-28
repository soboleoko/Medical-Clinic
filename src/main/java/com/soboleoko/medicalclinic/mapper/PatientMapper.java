package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.PatientDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO mapToPatientDTO(Patient patient);

    @InheritInverseConfiguration(name = "mapToPatientDTO")
    Patient mapToPatient(PatientDTO patientDTO);

    List<PatientDTO> mapToPatientDTOList(List<Patient> patientList);
}