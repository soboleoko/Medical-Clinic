package com.soboleoko.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class Doctor {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String password;
}
