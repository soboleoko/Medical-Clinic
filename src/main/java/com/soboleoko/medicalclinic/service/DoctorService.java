package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.DoctorAlreadyExistsException;
import com.soboleoko.medicalclinic.exception.DoctorNotFoundException;
import com.soboleoko.medicalclinic.exception.VisitNotFoundException;
import com.soboleoko.medicalclinic.model.*;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.repository.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final VisitRepository visitRepository;

    public Doctor addDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new DoctorAlreadyExistsException("Provided email is in use", HttpStatus.BAD_REQUEST);
        }
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).getContent();
    }

    @Transactional
    public Doctor updateDoctor(String email, Doctor newDoctorData) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        existingDoctor.setEmail(newDoctorData.getEmail());
        existingDoctor.setFirstName(newDoctorData.getFirstName());
        existingDoctor.setLastName(newDoctorData.getLastName());
        existingDoctor.setSpecialization(newDoctorData.getSpecialization());
        doctorRepository.save(existingDoctor);
        return existingDoctor;
    }

    @Transactional
    public void updatePassword(String email, UpdatePasswordDTO password) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        existingDoctor.setPassword(password.getPassword());
        doctorRepository.save(existingDoctor);
    }

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void deleteDoctor(String email) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        doctorRepository.delete(existingDoctor);
    }

    public Doctor createDoctorWithInstitution(CreateDoctorDTO createDoctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(createDoctorDTO.getFirstName());
        doctor.setLastName(createDoctorDTO.getLastName());
        doctor.setEmail(createDoctorDTO.getEmail());
        doctor.setPassword(createDoctorDTO.getPassword());
        doctor.setSpecialization(createDoctorDTO.getSpecialization());

        Institution institution = new Institution();
        institution.setName(createDoctorDTO.getInstitutionName());
        doctor.getInstitutions().add(institution);
        institution.getDoctors().add(doctor);
        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateDoctorAndVisitDate (UpdateDoctorAndVisitDateDTO updateDoctorAndVisitDateDTO, Long doctorId, Long visitId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        doctor.setFirstName(updateDoctorAndVisitDateDTO.getFirstName());
        doctor.setLastName(updateDoctorAndVisitDateDTO.getLastName());
        doctor.setSpecialization(updateDoctorAndVisitDateDTO.getSpecialization());

        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException("Visit does not exist", HttpStatus.NOT_FOUND));
        visit.setStartDate(updateDoctorAndVisitDateDTO.getVisitStartDate());
        visit.setEndDate(updateDoctorAndVisitDateDTO.getVisitEndDate());
        return doctor;
    }
}