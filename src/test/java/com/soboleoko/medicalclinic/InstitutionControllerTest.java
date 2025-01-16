package com.soboleoko.medicalclinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soboleoko.medicalclinic.model.CreateInstitutionDTO;
import com.soboleoko.medicalclinic.model.Doctor;
import com.soboleoko.medicalclinic.model.Institution;
import com.soboleoko.medicalclinic.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InstitutionService institutionService;

    @Test
    public void addInstitution_successfulPost_institutionReturned() throws Exception {
        CreateInstitutionDTO createInstitutionDTO = new CreateInstitutionDTO("newName");
        Institution institution = new Institution(null,"newName",new HashSet<>());
        Mockito.when(institutionService.addInstitution(any())).thenReturn(institution);

        mockMvc.perform(MockMvcRequestBuilders.post("/institutions")
                .content(objectMapper.writeValueAsString(createInstitutionDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newName"));
    }

    @Test
    public void assignDoctorToInstitution_successfulPatch_doctorAssigned() throws Exception {
        Institution institution = new Institution(1L,"newName",new HashSet<>());
        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
        Set<Doctor> doctors = Set.of(doctor);
        Institution institutionAssignedDoctor = new Institution(1L, "Inst1", doctors);

        Mockito.when(institutionService.assignDoctorToInstitution(doctor.getId(), institution.getId())).thenReturn(institutionAssignedDoctor);

        mockMvc.perform(MockMvcRequestBuilders.patch("/institutions/1/assign/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctors[0].email").value("newEmail@gmail.com"));
    }
}
