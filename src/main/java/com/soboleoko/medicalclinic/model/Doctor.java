package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "First name", nullable = false)
    private String firstName;
    @Column(name = "Last name", nullable = false)
    private String lastName;
    @Column(name = "Specialization", nullable = false)
    private String specialization;
    @Column(name = "Email", nullable = false)
    private String email;
    @Column(name = "Password")
    private String password;
}
