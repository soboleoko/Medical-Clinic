package com.soboleoko.medicalclinic;

import com.soboleoko.medicalclinic.exception.DoctorNotFoundException;
import com.soboleoko.medicalclinic.exception.InstitutionNotFoundException;
import com.soboleoko.medicalclinic.mapper.InstitutionMapper;
import com.soboleoko.medicalclinic.model.CreateInstitutionDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Institution;
import com.soboleoko.medicalclinic.repository.DoctorRepository;
import com.soboleoko.medicalclinic.repository.InstitutionRepository;
import com.soboleoko.medicalclinic.service.InstitutionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class InstitutionServiceTest {
    private DoctorRepository doctorRepository;
    private InstitutionRepository institutionRepository;
    private InstitutionService institutionService;
    private InstitutionMapper institutionMapper;

    @BeforeEach
    void setUp() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionMapper = Mockito.mock(InstitutionMapper.class);
        this.institutionService = new InstitutionService(institutionRepository, doctorRepository);
    }

    @Test
    public void addInstitution_successfulPost_institutionReturn() {
        //given
        CreateInstitutionDTO institution = new CreateInstitutionDTO("newName");
        Institution mappedInstitution = new Institution(null, "newName", new HashSet<>());
        when(institutionMapper.mapToInstitution(institution)).thenReturn(mappedInstitution);
        when(institutionRepository.save(mappedInstitution)).thenReturn(mappedInstitution);
        //when
        Institution addedInstitution = institutionService.addInstitution(mappedInstitution);
        //then
        Assertions.assertEquals("newName", addedInstitution.getName());
        Assertions.assertEquals(new HashSet<>(), addedInstitution.getDoctors());
    }

    @Test
    public void assignDoctorToInstitution_successfulPatch_doctorAssigned() {
        //given
        Institution mappedInstitution = new Institution(null, "newName", new HashSet<>());
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findById(null)).thenReturn(Optional.of(mappedDoctor));
        when(institutionRepository.findById(null)).thenReturn(Optional.of(mappedInstitution));
        //when
        Institution result = institutionService.assignDoctorToInstitution(null, null);
        //then
        Assertions.assertEquals("newName", result.getName());
        Assertions.assertEquals(1, result.getDoctors().size());
    }

    @Test
    public void assignDoctorToInstitution_throwDoctorNotFoundException_exceptionThrown() {
        //given
        when(doctorRepository.findByEmail("newEmail@gmail.com")).thenReturn(Optional.empty());
        //when
        DoctorNotFoundException result = Assertions.assertThrows(DoctorNotFoundException.class, () -> institutionService.assignDoctorToInstitution(null, null));
        //then
        Assertions.assertEquals("Doctor does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    public void assignDoctorToInstitution_throwInstitutionNotFoundException_exceptionThrown() {
        //given
        Doctor mappedDoctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        when(doctorRepository.findById(null)).thenReturn(Optional.of(mappedDoctor));
        when(institutionRepository.findById(null)).thenReturn(Optional.empty());
        //when
        InstitutionNotFoundException result = Assertions.assertThrows(InstitutionNotFoundException.class, () -> institutionService.assignDoctorToInstitution(null, null));
        //then
        Assertions.assertEquals("Institution does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }
}
