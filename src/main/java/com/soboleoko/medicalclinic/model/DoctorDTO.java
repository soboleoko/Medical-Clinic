package com.soboleoko.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorDTO {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
}
