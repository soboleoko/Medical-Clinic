package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patient")
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
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return id != null && id.equals(patient.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}