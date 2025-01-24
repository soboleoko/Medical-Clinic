package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.exception.DoctorAlreadyExistsException;
import com.soboleoko.medicalclinic.exception.DoctorNotFoundException;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {
    public DoctorRepository doctorRepository;
    public DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.doctorService = new DoctorService(doctorRepository);
    }

    @Test
    public void addDoctor_successfulCreate_doctorReturned() {
        //given
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        when(doctorRepository.save(mappedDoctor)).thenReturn(mappedDoctor);
        //when
        Doctor addedDoctor = doctorService.addDoctor(mappedDoctor);
        //then
        Assertions.assertEquals("newFirstName", addedDoctor.getFirstName());
        Assertions.assertEquals("newLastName", addedDoctor.getLastName());
        Assertions.assertEquals("newSpecialization", addedDoctor.getSpecialization());
        Assertions.assertEquals("newEmail@gmail.com", addedDoctor.getEmail());
        Assertions.assertEquals("newPassword", addedDoctor.getPassword());
        Assertions.assertEquals(new HashSet<>(), addedDoctor.getInstitutions());
        Assertions.assertEquals(new HashSet<>(), addedDoctor.getVisits());
    }

    @Test
    public void addDoctor_throwDoctorNotFoundException_exceptionThrown() {
        //given
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(mappedDoctor));
        //when
        DoctorAlreadyExistsException result = Assertions.assertThrows(DoctorAlreadyExistsException.class, () -> doctorService.addDoctor(mappedDoctor));
        //then
        Assertions.assertEquals("Provided email is in use", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    public void getDoctors_successfulGet_returnPatients() {
        //given
        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Doctor doctor1 = new Doctor(2L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findAll()).thenReturn(List.of(doctor, doctor1));
        //when
        List<Doctor> doctors = doctorService.getDoctors(any());
        //then
        Assertions.assertEquals(2, doctors.size());
    }

    @Test
    public void updateDoctor_successfulPut_returnUpdatedPatient() {
        //given
        Doctor mappedDoctor = new Doctor(1L, "newerFirstName", "newerLastName", "newerSpecialization", "newerEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(mappedDoctor));
        when(doctorRepository.save(mappedDoctor)).thenReturn(mappedDoctor);
        //when
        Doctor result = doctorService.updateDoctor("newEmail@gmail.com", mappedDoctor);
        //then
        Assertions.assertEquals("newerFirstName", result.getFirstName());
        Assertions.assertEquals("newerLastName", result.getLastName());
        Assertions.assertEquals("newerSpecialization", result.getSpecialization());
        Assertions.assertEquals("newerEmail@gmail.com", result.getEmail());
        Assertions.assertEquals("newPassword", result.getPassword());
        Assertions.assertEquals(new HashSet<>(), result.getVisits());
        Assertions.assertEquals(new HashSet<>(), result.getInstitutions());
    }

    @Test
    public void updateDoctor_throwDoctorDoesNotExist_exceptionThrown() {
        //given
        Doctor mappedDoctor = new Doctor(1L, "newerFirstName", "newerLastName", "newerSpecialization", "newerEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        //when
        DoctorNotFoundException result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.updateDoctor("newEmail@gmail.com", mappedDoctor));
        //then
        Assertions.assertEquals("Doctor does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void updatePassword_successfulPatch_passwordUpdated() {
        //given
        UpdatePasswordDTO newerPassword = new UpdatePasswordDTO("newerPassword");
        Doctor mappedDoctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(mappedDoctor));
        when(doctorRepository.save(mappedDoctor)).thenReturn(mappedDoctor);
        //when
        doctorService.updatePassword("newEmail@gmail.com", newerPassword);
        //then
        Assertions.assertEquals("newerPassword", mappedDoctor.getPassword());
        verify(doctorRepository).save(mappedDoctor);
    }

    @Test
    public void updatePassword_throwDoctorDoesNotExist_exceptionThrown() {
        //given
        UpdatePasswordDTO newerPassword = new UpdatePasswordDTO("newerPassword");
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        //when
        DoctorNotFoundException result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.updatePassword("newEmail@gmail.com", newerPassword));
        //then
        Assertions.assertEquals("Doctor does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void findByEmail_successfulGet_returnPatient() {
        //given
        Doctor mappedDoctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(mappedDoctor));
        //when
        Doctor result = doctorService.findByEmail("newEmail@gmail.com");
        //then
        Assertions.assertEquals("newFirstName", result.getFirstName());
        Assertions.assertEquals("newLastName", result.getLastName());
        Assertions.assertEquals("newSpecialization", result.getSpecialization());
        Assertions.assertEquals("newEmail@gmail.com", result.getEmail());
        Assertions.assertEquals("newPassword", result.getPassword());
        Assertions.assertEquals(new HashSet<>(), result.getInstitutions());
        Assertions.assertEquals(new HashSet<>(), result.getVisits());
    }

    @Test
    public void findByEmail_throwDoctorNotFoundException_exceptionThrown() {
        //given
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        //when
        DoctorNotFoundException result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.findByEmail("newEmail@gmail.com"));
        //then
        Assertions.assertEquals("Doctor does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void deleteDoctor_successfulDelete_doctorDeleted() {
        //given
        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.of(doctor));
        //when
        doctorService.deleteDoctor("newEmail@gmail.com");
        //then
        verify(doctorRepository).delete(doctor);
    }

    @Test
    public void deleteDoctor_throwDoctorNotFoundException_exceptionThrown() {
        //given
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        //when
        DoctorNotFoundException result = Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctor("newEmail@gmail.com"));
        //then
        Assertions.assertEquals("Doctor does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}