package com.soboleoko.medicalclinic.mapper;

import com.soboleoko.medicalclinic.model.Visit;
import com.soboleoko.medicalclinic.model.VisitDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitDTO mapToVisitDTO(Visit visit);

    List<VisitDTO> mapToVisitListDTO(List<Visit> visitList);
}