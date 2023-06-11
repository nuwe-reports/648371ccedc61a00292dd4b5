
package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Doctor d1, d2;

    @BeforeEach
    void setup() {
        d1 = new Doctor("Angela", "Davis", 35, "angela@gmail.com");
        d2 = new Doctor("Brad", "Pitt", 57, "brad@gmail.com");
    }

    @Test
    void testShouldGetAllDoctors() throws Exception {
        List<Doctor> doctors = Arrays.asList(d1, d2);

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetNoDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testShouldGetDoctorById() throws Exception {
        d1.setId(1);

        Optional<Doctor> optionalDoctor = Optional.of(d1);

        assertThat(optionalDoctor).isPresent();
        assertThat(optionalDoctor.get().getId()).isEqualTo(d1.getId());
        assertThat(d1.getId()).isEqualTo(1);

        when(doctorRepository.findById(d1.getId())).thenReturn(optionalDoctor);
        mockMvc.perform(get("/api/doctors/" + d1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetNoDoctorById() throws Exception {
        long id = 2;

        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldCreateDoctor() throws Exception {

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(d1)))
                .andExpect(status().isCreated());
    }

    @Test
    void testShouldDeleteDoctorById() throws Exception {
        d1.setId(1);

        Optional<Doctor> optionalDoctor = Optional.of(d1);

        assertThat(optionalDoctor).isPresent();
        assertThat(optionalDoctor.get().getId()).isEqualTo(d1.getId());
        assertThat(d1.getId()).isEqualTo(1);

        when(doctorRepository.findById(d1.getId())).thenReturn(optionalDoctor);
        mockMvc.perform(delete("/api/doctors/" + d1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldNotDeleteDoctorById() throws Exception {
        long id = 2;

        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldDeleteAllDoctors() throws Exception {

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient p1, p2;

    @BeforeEach
    void setup() {
        p1 = new Patient("Elton", "John", 67, "elton@gmail.com");
        p2 = new Patient("Freddy", "Mercury", 55, "freddy@gmail.com");
    }

    @Test
    void testShouldGetAllPatients() throws Exception {
        List<Patient> patients = Arrays.asList(p1, p2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetNoPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testShouldGetPatientById() throws Exception {
        p1.setId(1);

        Optional<Patient> optionalPatient = Optional.of(p1);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(p1.getId());
        assertThat(p1.getId()).isEqualTo(1);

        when(patientRepository.findById(p1.getId())).thenReturn(optionalPatient);
        mockMvc.perform(get("/api/patients/" + p1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetNoPatientById() throws Exception {
        long id = 2;

        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldCreatePatient() throws Exception {

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isCreated());
    }

    @Test
    void testShouldDeletePatientById() throws Exception {
        p1.setId(1);

        Optional<Patient> optionalPatient= Optional.of(p1);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(p1.getId());
        assertThat(p1.getId()).isEqualTo(1);

        when(patientRepository.findById(p1.getId())).thenReturn(optionalPatient);
        mockMvc.perform(delete("/api/patients/" + p1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldNotDeletePatientById() throws Exception {
        long id = 2;

        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldDeleteAllPatients() throws Exception {

        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Room r1, r2;

    @BeforeEach
    void setup() {
        r1 = new Room("Gynecology");
        r2 = new Room("Neurology");
    }

    @Test
    void testShouldGetAllRooms() throws Exception {
        List<Room> rooms = Arrays.asList(r1, r2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetNoRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testShouldGetRoomByName() throws Exception {
        Optional<Room> optionalRoom = Optional.of(r1);

        assertThat(optionalRoom).isPresent();
        assertThat(optionalRoom.get().getRoomName()).isEqualTo(r1.getRoomName());
        assertThat(r1.getRoomName()).isEqualTo("Gynecology");

        when(roomRepository.findByRoomName(r1.getRoomName())).thenReturn(optionalRoom);
        mockMvc.perform(get("/api/rooms/" + r1.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetNoRoomByName() throws Exception {
        String roomName = "Cardiology";

        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldCreateRoom() throws Exception {

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r1)))
                .andExpect(status().isCreated());
    }

    @Test
    void testShouldDeleteRoomByName() throws Exception {
        Optional<Room> optionalRoom = Optional.of(r1);

        assertThat(optionalRoom).isPresent();
        assertThat(optionalRoom.get().getRoomName()).isEqualTo(r1.getRoomName());
        assertThat(r1.getRoomName()).isEqualTo("Gynecology");

        when(roomRepository.findByRoomName(r1.getRoomName())).thenReturn(optionalRoom);
        mockMvc.perform(delete("/api/rooms/" + r1.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldNotDeleteRoomByName() throws Exception {
        String roomName = "Cardiology";

        mockMvc.perform(delete("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldDeleteAllRooms() throws Exception {

        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}
