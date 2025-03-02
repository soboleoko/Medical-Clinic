package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.DoctorNotFoundException;
import com.soboleoko.medicalclinic.exception.InstitutionNotFoundException;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Institution;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.repository.InstitutionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Institution addInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Transactional
    public Institution assignDoctorToInstitution(Long doctorID, Long institutionID) {
        Doctor doctor = doctorRepository.findById(doctorID)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.BAD_REQUEST));
        Institution institution = institutionRepository.findById(institutionID)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution does not exist", HttpStatus.BAD_REQUEST));
        institution.getDoctors().add(doctor);
        return institutionRepository.save(institution);
    }
}