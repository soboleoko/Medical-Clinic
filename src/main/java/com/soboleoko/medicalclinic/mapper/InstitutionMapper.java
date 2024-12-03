package com.soboleoko.medicalclinic.mapper;


import com.soboleoko.medicalclinic.model.CreateInstitutionDTO;
import com.soboleoko.medicalclinic.model.Institution;
import com.soboleoko.medicalclinic.model.InstitutionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    Institution mapToInstitution(CreateInstitutionDTO institutionDTO);

    InstitutionDTO mapToInstitutionDTO(Institution institution);
}
