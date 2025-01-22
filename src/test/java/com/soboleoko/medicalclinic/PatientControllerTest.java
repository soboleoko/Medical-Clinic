package com.soboleoko.medicalclinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soboleoko.medicalclinic.model.CreatePatientDTO;
import com.soboleoko.medicalclinic.model.Patient;
import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
import com.soboleoko.medicalclinic.service.PatientService;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Test
    public void addPatient_successfulPost_patientReturned() throws Exception {
        CreatePatientDTO createPatientDTO = new CreatePatientDTO("newEmail@gmail.con", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), "newPassword");
        Patient patient = new Patient(null, "newEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        Mockito.when(patientService.addPatient(any())).thenReturn(patient);
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .content(objectMapper.writeValueAsString(createPatientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newEmail@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCardNo").value("newCardNo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("newFirstName newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("newPhoneNumber"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1999-12-29"));
    }

    @Test
    public void getAllPatients_successfulGet_patientsReturned() throws Exception {
        Patient patient = new Patient(1L, "newEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        Patient patient1 = new Patient(2L, "anotherEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        List<Patient> patients = List.of(patient, patient1);
        Mockito.when(patientService.getPatients()).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("newEmail@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idCardNo").value("newCardNo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("newFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("newPhoneNumber"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthday").value("1999-12-29"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("anotherEmail@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idCardNo").value("newCardNo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("newFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].phoneNumber").value("newPhoneNumber"));
    }

    @Test
    public void getPatientByEmail_successfulGet_patientReturned() throws Exception {
        Patient patient = new Patient(1L, "newEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        Mockito.when(patientService.findByEmail("newEmail@gmail.com")).thenReturn(patient);
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{email}", "newEmail@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newEmail@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCardNo").value("newCardNo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("newFirstName newLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("newPhoneNumber"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1999-12-29"));
    }

    @Test
    public void updatePatient_successfulPut_dataUpdated() throws Exception {
        Patient updatedPatient = new Patient(null, "newerEmail@gmail.com", "newPassword", "newerCardNo", "newerFirstName", "newerLastName", "newerPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        Mockito.when(patientService.updatePatient("newEmail@gmail.com", updatedPatient)).thenReturn(updatedPatient);
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/{email}", "newEmail@gmail.com")
                        .content(objectMapper.writeValueAsString(updatedPatient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newerEmail@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCardNo").value("newerCardNo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newerFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newerLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("newerFirstName newerLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("newerPhoneNumber"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1999-12-29"));
    }

    @Test
    public void updatePassword_successfulPatch_passwordUpdated() throws Exception {
        UpdatePasswordDTO newPassword = new UpdatePasswordDTO("newerPassword");
        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/{email}", "newEmail@gmail.com")
                        .content(objectMapper.writeValueAsString(newPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletePatient_successfulDelete_patientDeleted() throws Exception {
        Patient patient = new Patient(1L, "newEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{email}", patient.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

