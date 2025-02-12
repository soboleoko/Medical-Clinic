package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ManyToMany(mappedBy = "doctors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Institution> institutions = new HashSet<>();
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return id != null && id.equals(doctor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}