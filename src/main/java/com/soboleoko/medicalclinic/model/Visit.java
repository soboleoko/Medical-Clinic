package com.soboleoko.medicalclinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    public static Visit of(CreateVisitDTO createVisitDTO, Doctor doctor, Institution institution) {
        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setStartDate(createVisitDTO.getStartDate());
        visit.setEndDate(createVisitDTO.getEndDate());
        visit.setInstitution(institution);

        return visit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;
        Visit visit = (Visit) o;
        return id != null && id.equals(visit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}