package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.PatientMapper;
import com.soboleoko.medicalclinic.model.CreatePatientDTO;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.service.PatientService;
import com.soboleoko.medicalclinic.model.PatientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping("/patients")
    @Operation(summary = "Receive a paginated patients list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public List<PatientDTO> getAllPatients(Pageable pageable) {
        return patientMapper.mapToPatientDTOList(patientService.getPatients(pageable));
    }

    @GetMapping("/patients/{email}")
    @Operation(summary = "Receive a patient by it's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Patient does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")

    })
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.mapToPatientDTO(patientService.findByEmail(email));
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create patient in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Patient already exists"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    private PatientDTO addPatient(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient to create",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreatePatientDTO.class),
                    examples = @ExampleObject(value = "{ \"firstName\" : \"Example First Name\", " +
                            "\"lastName\" : \"Example Last Name\", \"idCardNo\" : \"Example Card Number\"" +
                            " \"email\" : \"Example Email\", \"password\" : \"Example Password\"," +
                            " \"phoneNumber\" : \"Example Phone Number\",  \"birthday\" : \"Example Birthday\" }"))
    ) @RequestBody @Valid CreatePatientDTO patient) {
        return patientMapper.mapToPatientDTO(patientService.addPatient(patientMapper.mapToPatient(patient)));
    }

    @DeleteMapping("/patients/{email}")
    @Operation(summary = "Remove patient from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient removed"),
            @ApiResponse(responseCode = "400", description = "Patient does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public void deletePatientByEmail(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/patients/{email}")
    @Operation(summary = "Update patients' data in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient data updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Patient does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public PatientDTO updatePatient(@PathVariable String email, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient to update",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreatePatientDTO.class),
                    examples = @ExampleObject(value = "{ \"firstName\" : \"Example First Name\", " +
                            "\"lastName\" : \"Example Last Name\", \"idCardNo\" : \"Example Card Number\"" +
                            " \"email\" : \"Example Email\", \"password\" : \"Example Password\"," +
                            " \"phoneNumber\" : \"Example Phone Number\",  \"birthday\" : \"Example Birthday\" }"))
    ) @RequestBody @Valid CreatePatientDTO newPatientData) {
        return patientMapper.mapToPatientDTO(patientService.updatePatient(email, patientMapper.mapToPatient(newPatientData)));
    }

    @PatchMapping("/patients/{email}")
    @Operation(summary = "Update patients' password in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient password updated"),
            @ApiResponse(responseCode = "400", description = "Patient does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public void updatePassword(@PathVariable String email, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient to create",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UpdatePasswordDTO.class),
                    examples = @ExampleObject(value = "{ \"password\" : \"Example Password\" }"))
    ) @RequestBody @Valid UpdatePasswordDTO password) {
        patientService.updatePassword(email, password);
    }
}