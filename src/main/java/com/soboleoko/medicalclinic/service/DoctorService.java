package com.soboleoko.medicalclinic.service;

import com.soboleoko.medicalclinic.exception.DoctorAlreadyExistsException;
import com.soboleoko.medicalclinic.exception.DoctorNotFoundException;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Institution;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor addDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new DoctorAlreadyExistsException("Provided email is in use", HttpStatus.BAD_REQUEST);
        }
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor updateDoctor(String email, Doctor newDoctorData) {
        Doctor existingDoctor = doctorRepository.findByEmail(email).orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        existingDoctor.setEmail(newDoctorData.getEmail());
        existingDoctor.setFirstName(newDoctorData.getFirstName());
        existingDoctor.setLastName(newDoctorData.getLastName());
        existingDoctor.setSpecialization(newDoctorData.getSpecialization());
        doctorRepository.save(existingDoctor);
        return existingDoctor;
    }

    public void updatePassword(String email, UpdatePasswordDTO password) {
        Doctor existingDoctor = doctorRepository.findByEmail(email).orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        existingDoctor.setPassword(password.getPassword());
        doctorRepository.save(existingDoctor);
    }

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email).orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
    }

    public void deleteDoctor(String email) {
        Doctor existingDoctor = doctorRepository.findByEmail(email).orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist", HttpStatus.NOT_FOUND));
        doctorRepository.delete(existingDoctor);
    }
    public Doctor findDoctorById(Long id){
        return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor does not exist",
                HttpStatus.NOT_FOUND));
    }

    public Doctor assignDoctorToInstitution(Doctor doctor, Institution institution){
        doctor.setInstitution(institution);
        return doctorRepository.save(doctor);
    }
}
