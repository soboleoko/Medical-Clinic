package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.*;
import com.soboleoko.medicalclinic.model.*;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.repository.InstitutionRepository;
import com.soboleoko.medicalclinic.repository.PatientRepository;
import com.soboleoko.medicalclinic.repository.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final InstitutionRepository institutionRepository;

    @Transactional
    public Visit createVisit(CreateVisitDTO visit, Long doctorId, Long institutionId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.BAD_REQUEST));
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution does not exist", HttpStatus.BAD_REQUEST));
        checkAvailability(visit, doctor);
        Visit createdVisit = Visit.of(visit, doctor, institution);
        doctor.getVisits().add(createdVisit);
        return visitRepository.save(createdVisit);
    }

    @Transactional
    public Visit bookVisit(Long patientId, Long visitId) {
        Visit visitById = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit does not exist", HttpStatus.NOT_FOUND));
        if (visitById.getPatient() != null) {
            throw new VisitAlreadyBookedException("Provided visit is already booked", HttpStatus.BAD_REQUEST);
        }
        Patient patientById = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
        visitById.setPatient(patientById);
        if (visitById.getStartDate().isBefore(LocalDateTime.now())) {
            throw new VisitNotAvailableException("Booking in past is not allowed", HttpStatus.BAD_REQUEST);
        }
        return visitRepository.save(visitById);
    }

    public List<Visit> findPatientVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public void checkAvailability(CreateVisitDTO visit, Doctor doctor) {
        validateTime(visit.getStartDate());
        validateTime(visit.getEndDate());
        List<Visit> overlappingVisits = visitRepository.findOverlappingVisits(doctor.getId(), visit.getStartDate(), visit.getEndDate());
        if (!overlappingVisits.isEmpty()) {

            throw new VisitNotAvailableException("Provided date is already taken", HttpStatus.CONFLICT);
        }
    }

    private void validateTime(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        if (minutes % 15 != 0) {
            throw new VisitNotAvailableException("Visit time has to be in full quarters format",
                    HttpStatus.CONFLICT);
        }
    }
}