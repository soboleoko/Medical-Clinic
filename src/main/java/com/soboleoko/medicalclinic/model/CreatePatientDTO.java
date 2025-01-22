package com.soboleoko.medicalclinic.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePatientDTO {
    @NotBlank(message = "Please provide an email")
    @Email(message = "Wrong email format")
    private String email;
    @NotBlank(message = "Please provide an ID card number")
    private String idCardNo;
    @NotBlank(message = "Please provide a first name")
    private String firstName;
    @NotBlank(message = "Please provide a last name")
    private String lastName;
    @NotBlank(message = "Please provide a phone number")
    private String phoneNumber;
    @NotNull(message = "Please provide a birthday")
    private LocalDate birthday;
    @NotBlank(message = "Please provide a password")
    private String password;
}