package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.InstitutionNotFoundException;
import com.soboleoko.medicalclinic.model.Institution;
import com.soboleoko.medicalclinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public Institution addInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

    public Institution findInstitutionById(Long id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution does not exist",
                HttpStatus.NOT_FOUND));
    }
}
