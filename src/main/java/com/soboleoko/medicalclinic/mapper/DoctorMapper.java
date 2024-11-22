package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.CreateDoctorDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.DoctorDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor mapToDoctor(CreateDoctorDTO doctorDTO);

    DoctorDTO mapToDoctorDTO(Doctor doctor);

    List<DoctorDTO> mapToDoctorDTOList(List<Doctor> doctors);
}
