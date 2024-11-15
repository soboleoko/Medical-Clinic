package com.soboleoko.medicalclinic.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DoctorDTO {

    @NotBlank(message = "Please provide a first name")
    private String firstName;
    @NotBlank(message = "Please provide a last name")
    private String lastName;
    @NotBlank(message = "Please provide a specialization")
    private String specialization;
    @NotBlank(message = "Please provide an email")
    @Email(message = "Wrong email format")
    private String email;
}
