package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.exception.*;
import com.soboleoko.medicalclinic.mapper.VisitMapper;
import com.soboleoko.medicalclinic.model.CreateVisitDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.Visit;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.repository.PatientRepository;
import com.soboleoko.medicalclinic.repository.VisitRepository;
import com.soboleoko.medicalclinic.service.VisitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class VisitServiceTest {
    private VisitRepository visitRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private VisitMapper visitMapper;
    private VisitService visitService;

    @BeforeEach
    public void setUp() {
        this.visitRepository = Mockito.mock(VisitRepository.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.visitMapper = Mockito.mock(VisitMapper.class);
        this.visitService = new VisitService(visitRepository, patientRepository, doctorRepository);
    }

    @Test
    public void createVisit_successfulPost_returnVisit() {
        //given
        Patient mappedPatient = new Patient(null, "newEmail@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        CreateVisitDTO visit = new CreateVisitDTO(LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), null);
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), mappedPatient, mappedDoctor);
        when(visitMapper.mapToVisit(visit)).thenReturn(mappedVisit);
        when(doctorRepository.findById(null)).thenReturn(Optional.of(mappedDoctor));
        when(patientRepository.findById(null)).thenReturn(Optional.of(mappedPatient));
        when(visitRepository.save(mappedVisit)).thenReturn(mappedVisit);
        //when
        Visit createdVisit = visitService.createVisit(mappedVisit, null);
        //then
        Assertions.assertEquals(LocalDateTime.of(2025, 6, 16, 18, 0), createdVisit.getStartDate());
        Assertions.assertEquals(LocalDateTime.of(2025, 6, 16, 18, 30), createdVisit.getEndDate());
        Assertions.assertEquals(mappedPatient, createdVisit.getPatient());
        Assertions.assertEquals(mappedDoctor, createdVisit.getDoctor());
    }

    @Test
    public void createVisit_throwDoctorNotFoundException_exceptionThrown() {
        //given
        Patient mappedPatient = new Patient(null, "newEmail@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        CreateVisitDTO visit = new CreateVisitDTO(LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), null);
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), mappedPatient, null);
        when(visitMapper.mapToVisit(visit)).thenReturn(mappedVisit);
        when(patientRepository.findById(null)).thenReturn(Optional.of(mappedPatient));
        when(doctorRepository.findById(null)).thenReturn(Optional.empty());
        //when
        DoctorNotFoundException result = Assertions.assertThrows(DoctorNotFoundException.class, () -> visitService.createVisit(mappedVisit, null));
        //then
        Assertions.assertEquals("Doctor does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void createVisit_throwPatientNotFoundException_exceptionThrown() {
        //given
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Patient mappedPatient = new Patient(null, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        CreateVisitDTO visit = new CreateVisitDTO(LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), null);
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), mappedPatient, mappedDoctor);
        when(visitMapper.mapToVisit(visit)).thenReturn(mappedVisit);
        when(patientRepository.findById(null)).thenReturn(Optional.empty());
        when(doctorRepository.findById(null)).thenReturn(Optional.of(mappedDoctor));
        //when
        PatientNotFoundException result = Assertions.assertThrows(PatientNotFoundException.class, () -> visitService.createVisit(mappedVisit, null));
        //then
        Assertions.assertEquals("Patient does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void bookVisit_successfulPatch_visitBooked() {
        //given
        Patient mappedPatient = new Patient(null, "newEmail@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), null, mappedDoctor);
        when(visitRepository.findById(null)).thenReturn(Optional.of(mappedVisit));
        when(patientRepository.findById(null)).thenReturn(Optional.of(mappedPatient));
        //when
        Visit bookedVisit = visitService.bookVisit(mappedPatient.getId(), mappedVisit.getId());
        //then
        Assertions.assertEquals(LocalDateTime.of(2025, 6, 16, 18, 0), bookedVisit.getStartDate());
        Assertions.assertEquals(LocalDateTime.of(2025, 6, 16, 18, 30), bookedVisit.getEndDate());
        Assertions.assertEquals(mappedDoctor, bookedVisit.getDoctor());
    }

    @Test
    public void bookVisit_throwVisitAlreadyBookedException_exceptionThrown() {
        //given
        Patient mappedPatient = new Patient(null, "newEmail@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), mappedPatient, mappedDoctor);
        when(visitRepository.findById(null)).thenReturn(Optional.of(mappedVisit));
        when(patientRepository.findById(null)).thenReturn(Optional.of(mappedPatient));
        //when
        VisitAlreadyBookedException result = Assertions.assertThrows(VisitAlreadyBookedException.class, () -> visitService.bookVisit(null, null));
        //then
        Assertions.assertEquals("Provided visit is already booked", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    public void bookVisit_throwVisitNotFoundException_exceptionThrown() {
        //given
        when(visitRepository.findById(null)).thenReturn(Optional.empty());
        //when
        VisitNotFoundException result = Assertions.assertThrows(VisitNotFoundException.class, () -> visitService.bookVisit(null, null));
        //then
        Assertions.assertEquals("Visit does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void bookVisit_throwPatientNotFoundException_exceptionThrown() {
        //given
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), null, mappedDoctor);
        when(visitRepository.findById(null)).thenReturn(Optional.of(mappedVisit));
        when(patientRepository.findById(null)).thenReturn(Optional.empty());
        //when
        PatientNotFoundException result = Assertions.assertThrows(PatientNotFoundException.class, () -> visitService.bookVisit(null, mappedVisit.getId()));
        //then
        Assertions.assertEquals("Patient does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void bookVisit_throwVisitNotAvailableException_exceptionThrown() {
        //given
        Patient mappedPatient = new Patient(null, "newEmail@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2024, 6, 16, 18, 0), LocalDateTime.of(2024, 6, 16, 18, 30), null, null);
        when(visitRepository.findById(null)).thenReturn(Optional.of(mappedVisit));
        when(patientRepository.findById(null)).thenReturn(Optional.of(mappedPatient));
        //when
        VisitNotAvailableException result = Assertions.assertThrows(VisitNotAvailableException.class, () -> visitService.bookVisit(null, null));
        //then
        Assertions.assertEquals("Booking in past is not allowed", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    public void findPatientVisits_successfulGet_visitsReturned() {
        //given
        Patient mappedPatient = new Patient(null, "asd@gmail.com", "newPassword", "newIdCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29),
                new HashSet<>());
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Visit mappedVisit = new Visit(null, LocalDateTime.of(2025, 6, 16, 18, 0), LocalDateTime.of(2025, 6, 16, 18, 30), mappedPatient, mappedDoctor);
        when(visitRepository.findByPatientId(null)).thenReturn(List.of(mappedVisit));
        //when
        List<Visit> patientVisits = visitService.findPatientVisits(mappedPatient.getId());
        //then
        Assertions.assertEquals(1, patientVisits.size());
    }
}
