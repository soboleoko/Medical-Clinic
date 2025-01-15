package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.InstitutionMapper;
import com.soboleoko.medicalclinic.model.CreateInstitutionDTO;
import com.soboleoko.medicalclinic.model.InstitutionDTO;
import com.soboleoko.medicalclinic.service.InstitutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public InstitutionDTO addInstitution(@Valid @RequestBody CreateInstitutionDTO institution) {
        return institutionMapper.mapToInstitutionDTO(institutionService.addInstitution(institutionMapper.mapToInstitution(institution)));
    }

    @PatchMapping("{institutionId}/assign/{doctorId}")
    public InstitutionDTO assignDoctorToInstitution(@PathVariable Long doctorId, @PathVariable Long institutionId) {
        return institutionMapper.mapToInstitutionDTO(institutionService.assignDoctorToInstitution(doctorId, institutionId));
    }
}