package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.VisitMapper;
import com.soboleoko.medicalclinic.model.CreateVisitDTO;
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

    @PostMapping("/{doctorId}")
    public VisitDTO createVisit(@RequestBody @Valid CreateVisitDTO visit, @PathVariable Long doctorId) {
        return visitMapper.mapToVisitDTO(visitService.createVisit(visitMapper.mapToVisit(visit), doctorId));
    }

    @PatchMapping("/{visitId}/book")
    public VisitDTO bookVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        return visitMapper.mapToVisitDTO(visitService.bookVisit(visitId, patientId));
    }

    @GetMapping("/patients/{patientId}")
    public List<VisitDTO> getPatientVisits(@PathVariable Long patientId) {
        return visitMapper.mapToVisitListDTO(visitService.findPatientVisits(patientId));
    }

    @GetMapping("/check")
    public String checkIfAvailable(@RequestParam Long doctorId, @RequestParam LocalDateTime startDate,
                                   @RequestParam LocalDateTime endDate) {
        return visitService.checkAvailability(doctorId, startDate, endDate);
    }
}