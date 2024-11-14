package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.DoctorNotFoundException;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DoctorService {
    private final DoctorRepository doctorRepository;
    public List<Doctor> getDoctors() {
        return doctorRepository.getDoctors();
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.getDoctorByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
    }

    public Doctor addDoctor(Doctor doctor) {
        Optional<Doctor> doctorByEmail = doctorRepository.getDoctorByEmail(doctor.getEmail());
        if (doctorByEmail.isPresent()) {
            throw new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND);
        }
        return doctorRepository.addDoctor(doctor);
    }

    public void deleteDoctor(String email) {
        doctorRepository.deleteDoctor(email);
    }

    public Doctor updateDoctor(String email, Doctor newDoctorData) {
        return doctorRepository.updateDoctor(email,newDoctorData)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
    }
}
