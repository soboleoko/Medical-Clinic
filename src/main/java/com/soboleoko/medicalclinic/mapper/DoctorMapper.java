package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.CreateDoctorDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.DoctorDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "string")
public interface DoctorMapper {
    Doctor mapToDoctor (DoctorDTO doctorDTO);

    DoctorDTO mapToDoctorDTO (Doctor doctor);

    CreateDoctorDTO mapToCreateDoctorDTO (Doctor doctor);

    List<DoctorDTO> mapToDoctorDTOList (List<Doctor> doctors);
}