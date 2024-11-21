package com.soboleoko.medicalclinic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class Patient {
    @Column(name = "Email",length = 999,nullable = false)
    private String email;
    @Column(name = "Password",length = 999, nullable = false)
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idCardNo;
    @Column(name = "First name",length = 999,nullable = false)
    private String firstName;
    @Column(name = "Last name", length = 999,nullable = false)
    private String lastName;
    @Column(name = "Phone number", length = 999, nullable = false)
    private String phoneNumber;
    @Column(name = "Birthday", length = 999, nullable = false)
    private LocalDate birthday;
}

