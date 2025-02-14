package com.soboleoko.medicalclinic.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateInstitutionDTO {
    @NotBlank(message = "Please provide a name")
    private String name;
}