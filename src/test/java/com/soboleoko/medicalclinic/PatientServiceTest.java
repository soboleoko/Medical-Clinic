package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.exception.PatientAlreadyExistsException;
import com.soboleoko.medicalclinic.exception.PatientNotFoundException;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.repository.PatientRepository;
import com.soboleoko.medicalclinic.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {
    private PatientRepository patientRepository;
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientService = new PatientService(patientRepository);
    }

    // metodaKtoraTestuje_przypadekTestowy_spodziewanyRezultat
    @Test
    public void addPatient_successfulCreate_patientReturn() {
        //given
        Patient mappedPatient = new Patient(null, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        //when(patientRepository.findByEmail("asd@email.com")).thenReturn(patient);
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.empty());
//        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientRepository.save(mappedPatient)).thenReturn(mappedPatient);
        //when
        Patient addedPatient = patientService.addPatient(mappedPatient);
        //then
        Assertions.assertEquals("asd@gmail.com", addedPatient.getEmail());
        Assertions.assertEquals("newPassword", addedPatient.getPassword());
        Assertions.assertEquals("newIdCardNo", addedPatient.getIdCardNo());
        Assertions.assertEquals("newFirstName", addedPatient.getFirstName());
        Assertions.assertEquals("newLastName", addedPatient.getLastName());
        Assertions.assertEquals("newPhoneNumber", addedPatient.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(1999, 12, 29), addedPatient.getBirthday());
        Assertions.assertEquals(new HashSet<>(), addedPatient.getVisits());
    }

    @Test
    public void addPatient_throwPatientAlreadyExistsException_exceptionThrown() {
        //given
        Patient mappedPatient = new Patient(null, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.of(mappedPatient));
        //when
        PatientAlreadyExistsException result = Assertions.assertThrows(PatientAlreadyExistsException.class, () -> patientService.addPatient(mappedPatient));
        //then
        Assertions.assertEquals("Provided email is in use", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    public void getPatients_successfulGet_patientsReturn() {
        //given
        Patient mappedPatient = new Patient(1L, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        Patient mappedPatient1 = new Patient(2L, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(2000, 12, 29),
                new HashSet<>());
        when(patientRepository.findAll()).thenReturn(List.of(mappedPatient, mappedPatient1));
        //when
        List<Patient> patients = patientService.getPatients(any());
        //then
        Assertions.assertEquals(2, patients.size());
    }

    @Test
    public void updatePatient_successfulPut_returnUpdatedPatient() {
        //given
        Patient mappedPatient = new Patient(1L, "asd123@gmail.com", "newPassword", "newerIdCardNo", "newerFirstName", "newerLastName", "newerPhoneNumber", LocalDate.of(2000, 12, 29), new HashSet<>());
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.of(mappedPatient));
        when(patientRepository.save(mappedPatient)).thenReturn(mappedPatient);
        //when
        Patient result = patientService.updatePatient("asd@gmail.com", mappedPatient);
        //then
        Assertions.assertEquals("asd123@gmail.com", result.getEmail());
        Assertions.assertEquals("newerIdCardNo", result.getIdCardNo());
        Assertions.assertEquals("newerFirstName", result.getFirstName());
        Assertions.assertEquals("newerLastName", result.getLastName());
        Assertions.assertEquals("newerPhoneNumber", result.getPhoneNumber());
        Assertions.assertEquals("newPassword", result.getPassword());
        Assertions.assertEquals(LocalDate.of(2000, 12, 29), result.getBirthday());
        Assertions.assertEquals(new HashSet<>(), result.getVisits());
    }

    @Test
    public void updatePatient_throwPatientNotFoundException_exceptionThrown() {
        //given
        Patient mappedPatient = new Patient(1L, "asd123@gmail.com", "newPassword", "newerIdCardNo", "newerFirstName", "newerLastName", "newerPhoneNumber", LocalDate.of(2000, 12, 29), new HashSet<>());
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.empty());
        //when
        PatientNotFoundException result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient("asd@gmail.com", mappedPatient));
        //then
        Assertions.assertEquals("Patient does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void updatePassword_successfulPatch_passwordUpdated() {
        //given
        UpdatePasswordDTO newerPassword = new UpdatePasswordDTO("newerPassword");
        Patient mappedPatient = new Patient(1L, "asd@gmail.com", "newerPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.of(mappedPatient));
        when(patientRepository.save(mappedPatient)).thenReturn(mappedPatient);
        //when
        patientService.updatePassword("asd@gmail.com", newerPassword);
        //then
        Assertions.assertEquals("newerPassword", mappedPatient.getPassword());
        verify(patientRepository).save(mappedPatient);
    }

    @Test
    public void updatePassword_throwPatientNotFoundException_exceptionThrown() {
        //given
        UpdatePasswordDTO newerPassword = new UpdatePasswordDTO("newerPassword");
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.empty());
        //when
        PatientNotFoundException result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.updatePassword("asd@gmail.com", newerPassword));
        //then
        Assertions.assertEquals("Patient does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void findByEmail_successfulGet_returnPatient() {
        //given
        Patient mappedPatient = new Patient(1L, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.of(mappedPatient));
        //when
        Patient foundPatient = patientService.findByEmail("asd@gmail.com");
        //then
        Assertions.assertEquals("asd@gmail.com", foundPatient.getEmail());
        Assertions.assertEquals("newPassword", foundPatient.getPassword());
        Assertions.assertEquals("newIdCardNo", foundPatient.getIdCardNo());
        Assertions.assertEquals("newFirstName", foundPatient.getFirstName());
        Assertions.assertEquals("newLastName", foundPatient.getLastName());
        Assertions.assertEquals("newPhoneNumber", foundPatient.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(1999, 12, 29), foundPatient.getBirthday());
        Assertions.assertEquals(new HashSet<>(), foundPatient.getVisits());
    }

    @Test
    public void findByEmail_throwPatientNotFoundException_exceptionThrown() {
        //given
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.empty());
        //when
        PatientNotFoundException result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.findByEmail("asd@gmail.com"));
        //then
        Assertions.assertEquals("Patient does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void deletePatient_successfulDelete_patientDeleted() {
        //when
        Patient patient = new Patient(1L, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.of(patient));
        //when
        patientService.deletePatient("asd@gmail.com");
        //then
        verify(patientRepository).delete(patient);
    }

    @Test
    public void deletePatient_throwPatientNotFoundException_exceptionThrown() {
        //given
        when(patientRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.empty());
        //when
        PatientNotFoundException result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.deletePatient("asd@gmail.com"));
        //then
        Assertions.assertEquals("Patient does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}
