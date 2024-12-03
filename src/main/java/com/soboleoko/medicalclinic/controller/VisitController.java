package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.VisitMapper;
import com.soboleoko.medicalclinic.model.VisitDTO;
import com.soboleoko.medicalclinic.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @PostMapping("/create")
    public VisitDTO createVisit(@Valid @RequestParam Long doctorId, @Valid @RequestParam LocalDateTime dateTime) {
        return visitMapper.mapToVisitDTO(visitService.createVisit(doctorId, dateTime));
    }

    @PatchMapping("/{visitId}/book")
    public VisitDTO bookVisit(@Valid @PathVariable Long visitId, @Valid @RequestParam Long patientId) {
        return visitMapper.mapToVisitDTO(visitService.bookVisit(visitId, patientId));
    }

    @GetMapping("/patient/{patientId}/")
    public List<VisitDTO> getPatientVisits(@Valid @PathVariable Long patientId) {
        return visitMapper.mapToVisitListDTO(visitService.findPatientVisits(patientId));
    }

}
