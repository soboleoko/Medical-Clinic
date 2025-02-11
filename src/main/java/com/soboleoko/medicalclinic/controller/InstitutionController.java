package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.exception.ErrorMessage;
import com.soboleoko.medicalclinic.mapper.InstitutionMapper;
import com.soboleoko.medicalclinic.model.CreateInstitutionDTO;
import com.soboleoko.medicalclinic.model.InstitutionDTO;
import com.soboleoko.medicalclinic.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create institution in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Institution created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstitutionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Institution already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public InstitutionDTO addInstitution(@Valid @RequestBody CreateInstitutionDTO institution) {
        return institutionMapper.mapToInstitutionDTO(institutionService.addInstitution(institutionMapper.mapToInstitution(institution)));
    }

    @Operation(summary = "Provide information to which institution doctor belongs")
    @PatchMapping("{institutionId}/assign/{doctorId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor assigned to institution",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstitutionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Doctor or institution does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Unknown error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public InstitutionDTO assignDoctorToInstitution(@PathVariable Long doctorId, @PathVariable Long institutionId) {
        return institutionMapper.mapToInstitutionDTO(institutionService.assignDoctorToInstitution(doctorId, institutionId));
    }
}