package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.VisitMapper;
import com.soboleoko.medicalclinic.model.CreateVisitDTO;
import com.soboleoko.medicalclinic.model.VisitDTO;
import com.soboleoko.medicalclinic.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public VisitDTO createVisit(@RequestBody @Valid CreateVisitDTO createVisitDTO) {
        return visitMapper.mapToVisitDTO(visitService.createVisit((createVisitDTO)));
    }

    @PatchMapping("/{visitId}/book")
    public VisitDTO bookVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        return visitMapper.mapToVisitDTO(visitService.bookVisit(visitId, patientId));
    }

    @GetMapping("/patients/{patientId}")
    public List<VisitDTO> getPatientVisits(@PathVariable Long patientId) {
        return visitMapper.mapToVisitListDTO(visitService.findPatientVisits(patientId));
    }
}