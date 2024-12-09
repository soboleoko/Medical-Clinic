package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "First_name", nullable = false)
    private String firstName;
    @Column(name = "Last_name", nullable = false)
    private String lastName;
    @Column(name = "Specialization", nullable = false)
    private String specialization;
    @Column(name = "Email", nullable = false)
    private String email;
    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "doctors")
    private Set<Institution> institutions = new HashSet<>();
    @OneToMany(mappedBy = "doctor")
    private Set<Visit> visits = new HashSet<>();
}