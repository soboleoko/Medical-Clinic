package com.soboleoko.medicalclinic.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateVisitDTO {
    @NotBlank(message = "Please provide a correct date and time")
    private LocalDateTime dateTime;
}
