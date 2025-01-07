package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.*;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.Visit;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
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

    @Transactional
    public Visit createVisit(Visit createVisitDTO, Long doctorId) {
        Visit visit = new Visit();
        visit.setStartDate(createVisitDTO.getStartDate());
        visit.setEndDate(createVisitDTO.getEndDate());
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        visit.setDoctor(doctor);
        if (createVisitDTO.getPatient() != null) {
            Patient patient = patientRepository.findById(createVisitDTO.getPatient().getId())
                    .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
            visit.setPatient(patient);
        } else {
            visit.setPatient(null);
        }
        return visitRepository.save(visit);
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
        return visitById;
    }

    public List<Visit> findPatientVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public String checkAvailability(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Visit> overlappingVisits = visitRepository.findOverlappingVisits(doctorId, startDate, endDate);
        if (!overlappingVisits.isEmpty()) {
            throw new VisitNotAvailableException("Provided date is already taken", HttpStatus.CONFLICT);
        }
        return "Provided date is free";
    }
}