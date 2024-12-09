package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Email", length = 100, nullable = false)
    private String email;
    @Column(name = "Password", length = 100, nullable = false)
    private String password;
    @Column(name = "ID_Card_Number", length = 100, nullable = false)
    private String idCardNo;
    @Column(name = "First_name", length = 100, nullable = false)
    private String firstName;
    @Column(name = "Last_name", length = 100, nullable = false)
    private String lastName;
    @Column(name = "Phone_number", length = 100, nullable = false)
    private String phoneNumber;
    @Column(name = "Birthday", length = 100, nullable = false)
    private LocalDate birthday;
    @OneToMany(mappedBy = "patient")
    private Set<Visit> visits = new HashSet<>();
}

