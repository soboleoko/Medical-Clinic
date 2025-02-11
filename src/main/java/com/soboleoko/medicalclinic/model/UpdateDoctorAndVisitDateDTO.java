package com.soboleoko.medicalclinic.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDoctorAndVisitDateDTO {
    @NotBlank(message = "Please provide a first name")
    private String firstName;
    @NotBlank(message = "Please provide a last name")
    private String lastName;
    @NotBlank(message = "Please provide a specialization")
    private String specialization;
    @Future(message = "Visit in past is not allowed")
    @NotNull(message = "Please provide a correct date and time")
    private LocalDateTime visitStartDate;
    @Future(message = "Visit in past is not allowed")
    @NotNull(message = "Please provide a correct date and time")
    private LocalDateTime visitEndDate;
}
