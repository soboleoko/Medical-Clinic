package com.soboleoko.medicalclinic.repository;

import com.soboleoko.medicalclinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientDAORepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByIdCardNumber(String idCardNo);

    @Query("UPDATE Patient patient SET patient.password = :password WHERE patient.idCardNo = LOWER(:idCardNo)")
    void updatePasswordByIdCardNumber(@Param("password") String password, @Param("id") String idCardNumber);
}
