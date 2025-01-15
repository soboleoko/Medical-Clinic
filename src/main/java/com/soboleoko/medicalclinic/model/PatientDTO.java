package com.soboleoko.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private long id;
    private String email;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private LocalDate birthday;
}