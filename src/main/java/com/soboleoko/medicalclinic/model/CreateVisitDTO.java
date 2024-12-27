package com.soboleoko.medicalclinic.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateVisitDTO {
    @Future(message = "Visit in past is not allowed")
    @NotNull(message = "Please provide a correct date and time")
    private LocalDateTime startDate;
    @Future(message = "Visit in past is not allowed")
    @NotNull(message = "Please provide a correct date and time")
    private LocalDateTime endDate;
    private Long patientId;
}