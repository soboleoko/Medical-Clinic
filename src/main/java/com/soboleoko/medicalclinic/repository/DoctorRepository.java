package com.soboleoko.medicalclinic.repository;

import com.soboleoko.medicalclinic.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    @EntityGraph(value = "doctor-graph-with-visits", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT d FROM Doctor d")
    Page<Doctor> findAllWithVisits(Pageable pageable);
}