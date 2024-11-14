package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.DoctorDTO;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.PatientDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalClinicMapper {
    @Mapping(source = "patient", target = "fullName", qualifiedByName = "connectNames")
    PatientDTO mapToPatientDTO(Patient patient);

    @InheritInverseConfiguration(name = "mapToPatientDTO")
    Patient mapToPatient(PatientDTO patientDTO);

    @Mapping(source = "patient", target = "fullName", qualifiedByName = "connectNames")
    List<PatientDTO> mapToPatientDTOList(List<Patient> patientList);

    @Named("connectNames")
    static String connectNames (Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }

    Doctor mapToDoctor (DoctorDTO doctorDTO);

    @InheritInverseConfiguration(name = "mapToDoctor")
    DoctorDTO mapToDoctorDTO (Doctor doctor);

    List<DoctorDTO> mapToDoctorDTOList (List<Doctor> doctors);
}