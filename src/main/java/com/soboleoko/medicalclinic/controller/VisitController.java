package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.exception.ErrorMessage;
import com.soboleoko.medicalclinic.mapper.VisitMapper;
import com.soboleoko.medicalclinic.model.CreateVisitDTO;
import com.soboleoko.medicalclinic.model.VisitDTO;
import com.soboleoko.medicalclinic.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create visit in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Doctor or institution does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Unknown error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public VisitDTO createVisit(@RequestBody @Valid CreateVisitDTO createVisitDTO) {
        return visitMapper.mapToVisitDTO(visitService.createVisit((createVisitDTO)));
    }

    @PatchMapping("/{visitId}/book")
    @Operation(summary = "Assign patient to visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit booked",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Provided visit is already booked",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Unknown error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public VisitDTO bookVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        return visitMapper.mapToVisitDTO(visitService.bookVisit(visitId, patientId));
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Receive list of all visits assigned to patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients' visits returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unknown error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public List<VisitDTO> getPatientVisits(@PathVariable Long patientId) {
        return visitMapper.mapToVisitListDTO(visitService.findPatientVisits(patientId));
    }
}