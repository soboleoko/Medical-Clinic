package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.InstitutionMapper;
import com.soboleoko.medicalclinic.model.CreateInstitutionDTO;
import com.soboleoko.medicalclinic.model.InstitutionDTO;
import com.soboleoko.medicalclinic.service.InstitutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @PostMapping
    public InstitutionDTO addInstitution(@Valid @RequestBody CreateInstitutionDTO institution) {
        return institutionMapper.mapToInstitutionDTO(institutionService.addInstitution(institutionMapper.mapToInstitution(institution)));
    }
}