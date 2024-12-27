package com.soboleoko.medicalclinic.repository;

import com.soboleoko.medicalclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatientId(Long patientId);

    @Query(value = "SELECT v FROM Visit v WHERE v.doctor.id = :doctorId AND v.startDate < :endDate " +
            "AND v.endDate > :startDate")
    List<Visit> findOverlappingVisits(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
}