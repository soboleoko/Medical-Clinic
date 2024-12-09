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
public class CreateVisitDTO {
    @Future(message = "Visit in past is not allowed")
    @NotNull(message = "Please provide a correct date and time")
    private LocalDateTime dateTime;
    @NotBlank(message = "Please provide a correct doctor ID")
    private Long doctorId;
    private Long patientId;
}
