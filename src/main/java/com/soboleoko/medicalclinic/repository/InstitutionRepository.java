package com.soboleoko.medicalclinic.repository;

import com.soboleoko.medicalclinic.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}