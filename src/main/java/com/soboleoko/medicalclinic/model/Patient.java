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
    private Long id;
    @Column(name = "Email",length = 100,nullable = false)
    private String email;
    @Column(name = "Password",length = 100, nullable = false)
    private String password;
    @Column(name = "ID Card Number",length = 100,nullable = false)
    private String idCardNo;
    @Column(name = "First name",length = 100,nullable = false)
    private String firstName;
    @Column(name = "Last name", length = 100,nullable = false)
    private String lastName;
    @Column(name = "Phone number", length = 100, nullable = false)
    private String phoneNumber;
    @Column(name = "Birthday", length = 100, nullable = false)
    private LocalDate birthday;
}

