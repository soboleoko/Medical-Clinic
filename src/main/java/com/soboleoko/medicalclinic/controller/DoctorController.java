package com.soboleoko.medicalclinic.controller;

import com.soboleoko.medicalclinic.mapper.DoctorMapper;
import com.soboleoko.medicalclinic.model.*;
import com.soboleoko.medicalclinic.service.DoctorService;
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


@RequestMapping("/doctors")
@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;

    @GetMapping
    @Operation(summary = "Receive a paginated doctors list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public List<DoctorDTO> getDoctors(Pageable pageable) {
        return doctorMapper.mapToDoctorDTOList(doctorService.getDoctors(pageable));
    }

    @GetMapping("/{email}")
    @Operation(summary = "Receive a doctor by its' email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return doctorMapper.mapToDoctorDTO(doctorService.findByEmail(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create doctor in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Doctor already exists"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public DoctorDTO addDoctor(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Doctor to create",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreateDoctorDTO.class),
                    examples = @ExampleObject(value = "{ \"firstName\" : \"Example First Name\", " +
                            "\"lastName\" : \"Example Last Name\", \"specialization\" : \"Example Specialization\"" +
                            " \"email\" : \"Example Email\", \"password\" : \"Example Password\" }"))
    ) @RequestBody @Valid CreateDoctorDTO doctor) {
        return doctorMapper.mapToDoctorDTO(doctorService.addDoctor(doctorMapper.mapToDoctor(doctor)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    @Operation(summary = "Remove doctor from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor removed"),
            @ApiResponse(responseCode = "400", description = "Doctor does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public void deleteDoctor(@PathVariable String email) {
        doctorService.deleteDoctor(email);
    }

    @PutMapping("/{email}")
    @Operation(summary = "Update doctors' data in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor data updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Doctor does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public DoctorDTO updateDoctor(@PathVariable String email,
                                  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                          description = "Doctor to update",
                                          content = @Content(mediaType = "application/json",
                                                  schema = @Schema(implementation = CreateDoctorDTO.class),
                                                  examples = @ExampleObject(value = "{ \"firstName\" : \"Example First Name\", " +
                                                          "\"lastName\" : \"Example Last Name\", \"specialization\" : \"Example Specialization\"" +
                                                          " \"email\" : \"Example Email\", \"password\" : \"Example Password\" }"))
                                  ) @RequestBody @Valid CreateDoctorDTO newDoctorData) {
        return doctorMapper.mapToDoctorDTO(doctorService.updateDoctor(email, doctorMapper.mapToDoctor(newDoctorData)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{email}")
    @Operation(summary = "Update doctors' password in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor password updated"),
            @ApiResponse(responseCode = "400", description = "Doctor does not exist"),
            @ApiResponse(responseCode = "500", description = "Unknown error")
    })
    public void updatePassword(@PathVariable String email,
                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                       description = "Password to update",
                                       content = @Content(mediaType = "application/json",
                                               schema = @Schema(implementation = UpdatePasswordDTO.class),
                                               examples = @ExampleObject(value = "{\"password\" : \"Example Password\" }"))
                               ) @RequestBody @Valid UpdatePasswordDTO password) {
        doctorService.updatePassword(email, password);
    }
}