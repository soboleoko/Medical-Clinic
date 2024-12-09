package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.*;
import com.soboleoko.medicalclinic.model.CreateVisitDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.Visit;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.repository.PatientRepository;
import com.soboleoko.medicalclinic.repository.VisitRepository;
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

    public Visit createVisit(CreateVisitDTO createVisitDTO,Long doctorId) {
        Visit visit = new Visit();
        visit.setDateTime(createVisitDTO.getDateTime());
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        visit.setDoctor(doctor);
        if (createVisitDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(createVisitDTO.getPatientId())
                    .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
            visit.setPatient(patient);
        }else {
            visit.setPatient(null);
        }
        return visitRepository.save(visit);
    }

    public Visit bookVisit(Long patientId, Long visitId) {
        Visit visitById = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit does not exist", HttpStatus.NOT_FOUND));
        if (visitById.getPatient() != null) {
            throw new VisitAlreadyBookedException("Provided visit is already booked", HttpStatus.BAD_REQUEST);
        }
        Patient patientById = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(HttpStatus.NOT_FOUND, "Patient does not exist"));
        visitById.setPatient(patientById);
        if (visitById.getDateTime().isBefore(LocalDateTime.now())) {
            throw new VisitNotAvailableException("Booking in past is not allowed", HttpStatus.BAD_REQUEST);
        }
        return visitRepository.save(visitById);
    }

    public List<Visit> findPatientVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }
}
