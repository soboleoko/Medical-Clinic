package com.soboleoko.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstitutionDTO {
    private String name;
    private Set<Doctor> doctors;
}