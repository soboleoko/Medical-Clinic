package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.CreatePatientDTO;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "patient", target = "fullName", qualifiedByName = "connectNames")
    PatientDTO mapToPatientDTO(Patient patient);

    Patient mapToPatient(CreatePatientDTO patientDTO);

    @Mapping(source = "patient", target = "fullName", qualifiedByName = "connectNames")
    List<PatientDTO> mapToPatientDTOList(List<Patient> patientList);

    @Named("connectNames")
    static String connectNames(Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }
}