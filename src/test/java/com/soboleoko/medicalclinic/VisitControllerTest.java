//package com.soboleoko.medicalclinic;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.soboleoko.medicalclinic.model.CreateVisitDTO;
//import com.soboleoko.medicalclinic.model.Doctor;
//import com.soboleoko.medicalclinic.model.Patient;
//import com.soboleoko.medicalclinic.model.Visit;
//import com.soboleoko.medicalclinic.service.VisitService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.HashSet;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class VisitControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private VisitService visitService;
//
//    @Test
//    public void createVisit_successfulPost_visitReturned() throws Exception {
//        Patient patient = new Patient(1L, "newEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
//        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        CreateVisitDTO createVisitDTO = new CreateVisitDTO(LocalDateTime.of(2030, 1, 1, 15, 30), LocalDateTime.of(2030, 1, 1, 16, 0), patient.getId(), doctor.getId());
//        Visit visit = new Visit(null, LocalDateTime.of(2030, 1, 1, 15, 30), LocalDateTime.of(2030, 1, 1, 16, 0), patient, doctor);
//
//        when(visitService.createVisit(visit, doctor.getId())).thenReturn(visit);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/visits/1")
//                        .content(objectMapper.writeValueAsString(createVisitDTO))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2030-01-01T15:30"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2030-01-01T16:00"));
//    }
//
//    @Test
//    public void bookVisit_successfulPatch_visitBooked() throws Exception {
//        Patient patient = new Patient(1L, "newEmail@gmail.com", "newPassword", "newCardNo", "newFirstName", "newLastName", "newPhoneNumber", LocalDate.of(1999, 12, 29), new HashSet<>());
//        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        Visit visit = new Visit(1L, LocalDateTime.of(2030, 1, 1, 15, 30), LocalDateTime.of(2030, 1, 1, 16, 0), patient, doctor);
//
//        when(visitService.bookVisit(patient.getId(), visit.getId())).thenReturn(visit);
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/1/book").param("patientId", "1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("patientId").value(1L));
//    }
//}
