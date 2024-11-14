package com.soboleoko.medicalclinic.repository;

import com.soboleoko.medicalclinic.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DoctorRepository {
    public final List<Doctor> doctors = new ArrayList<>();


    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctors.stream()
                .filter(doctor -> doctor.getEmail().equals(email))
                .findFirst();
    }

    public Doctor addDoctor(Doctor doctor) {
        doctors.add(doctor);
        return doctor;
    }

    public void deleteDoctor(String email) {
        doctors.removeIf(doctor -> doctor.getEmail().equals(email));
    }
    public Optional<Doctor> updateDoctor(String email, Doctor newDoctorData) {
        Optional<Doctor> existingDoctor = getDoctorByEmail(email);
        if (existingDoctor.isPresent()) {
            Doctor doctor = existingDoctor.get();
            doctor.setEmail(newDoctorData.getEmail());
            doctor.setFirstName(newDoctorData.getFirstName());
            doctor.setLastName(newDoctorData.getLastName());
            doctor.setSpecialization(newDoctorData.getSpecialization());
        }
        return existingDoctor;
    }
}
