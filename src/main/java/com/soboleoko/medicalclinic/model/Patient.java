package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
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
}

