//package com.soboleoko.medicalclinic;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.soboleoko.medicalclinic.model.CreateDoctorDTO;
//import com.soboleoko.medicalclinic.model.Doctor;
//import com.soboleoko.medicalclinic.model.UpdatePasswordDTO;
//import com.soboleoko.medicalclinic.service.DoctorService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.HashSet;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class DoctorControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private DoctorService doctorService;
//
//    @Test
//    public void addDoctor_successfulPost_patientReturned() throws Exception {
//        CreateDoctorDTO createDoctorDTO = new CreateDoctorDTO("newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword");
//        Doctor doctor = new Doctor(null, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        Mockito.when(doctorService.addDoctor(any())).thenReturn(doctor);
//        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
//                        .content(objectMapper.writeValueAsString(createDoctorDTO))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newFirstName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newLastName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value("newSpecialization"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newEmail@gmail.com"));
//    }
//
//    @Test
//    public void getAllPatients_successfulGet_patientsReturned() throws Exception {
//        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        Doctor anotherDoctor = new Doctor(2L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        List<Doctor> doctors = List.of(doctor, anotherDoctor);
//        Mockito.when(doctorService.getDoctors(any())).thenReturn(doctors);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("newFirstName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("newLastName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].specialization").value("newSpecialization"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("newEmail@gmail.com"))
//
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("newFirstName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("newLastName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].specialization").value("newSpecialization"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("newEmail@gmail.com"));
//    }
//
//
//    @Test
//    public void getDoctorByEmail_successfulGet_doctorReturned() throws Exception {
//        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        Mockito.when(doctorService.findByEmail("newEmail@gmail.com")).thenReturn(doctor);
//        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{email}", "newEmail@gmail.com"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newFirstName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newLastName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value("newSpecialization"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newEmail@gmail.com"));
//    }
//
//    @Test
//    public void updateDoctor_successfulPut_dataUpdated() throws Exception {
//        Doctor updatedDoctor = new Doctor(null, "newerFirstName", "newerLastName", "newerSpecialization", "newerEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        Mockito.when(doctorService.updateDoctor("newEmail@gmail.com", updatedDoctor)).thenReturn(updatedDoctor);
//        mockMvc.perform(MockMvcRequestBuilders.put("/doctors/{email}", "newEmail@gmail.com")
//                        .content(objectMapper.writeValueAsString(updatedDoctor))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("newerFirstName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("newerLastName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value("newerSpecialization"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newerEmail@gmail.com"));
//    }
//
//    @Test
//    public void updatePassword_successfulPatch_passwordUpdated() throws Exception {
//        UpdatePasswordDTO newPassword = new UpdatePasswordDTO("newerPassword");
//        mockMvc.perform(MockMvcRequestBuilders.patch("/doctors/{email}", "newEmail@gmail.com")
//                        .content(objectMapper.writeValueAsString(newPassword))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//        Mockito.verify(doctorService).updatePassword("newEmail@gmail.com",newPassword);
//    }
//
//    @Test
//    public void deleteDoctor_successfulDelete_patientDeleted() throws Exception {
//        Doctor doctor = new Doctor(1L, "newFirstName", "newLastName", "newSpecialization", "newEmail@gmail.com", "newPassword", new HashSet<>(), new HashSet<>());
//        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/{email}", doctor.getEmail()))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//        Mockito.verify(doctorService).deleteDoctor("newEmail@gmail.com");
//    }
//}