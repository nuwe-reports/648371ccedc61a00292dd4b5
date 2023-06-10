package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1, d2;
	private Patient p1, p2;
    private Room r1, r2;
    private Appointment a1, a2, a3, a4, a5, a6;
    private LocalDateTime startsAt, startsAt2, startsAt3, startsAt4;
    private LocalDateTime finishesAt, finishesAt2, finishesAt3, finishesAt4;

    @BeforeEach
    void setup() {
        d1 = new Doctor("Richard", "Gere", 56, "richard@gmail.com");
        d2 = new Doctor("Angelina", "Jolie", 32, "angelina@gmail.com");
        p1 = new Patient("Meryl", "Streep", 49, "meryl@gmail.com");
        p2 = new Patient("Orlando", "Bloom", 42, "orlando@gmail.com");
        r1 = new Room("Cardiology");
        r2 = new Room("Dermatology");
        startsAt = LocalDateTime.of(2023, 6, 10, 9, 0);
        startsAt2 = LocalDateTime.of(2023, 6, 11, 10, 00);
        startsAt3 = LocalDateTime.of(2023, 6, 10, 9, 30);
        startsAt4 = LocalDateTime.of(2023, 6, 10, 8, 0);
        finishesAt = LocalDateTime.of(2023, 6, 10, 10, 0);
        finishesAt2 = LocalDateTime.of(2023, 6, 11, 11, 0);
        finishesAt3 = LocalDateTime.of(2023, 6, 10, 10, 30);
        finishesAt4 = LocalDateTime.of(2023, 6, 10, 11, 0);
    }

    @Test
    void testDoctorEntity() {
        entityManager.persistAndFlush(d1);

        Doctor savedDoctor = entityManager.find(Doctor.class, d1.getId());

        assertEquals(d1.getId(), savedDoctor.getId());
        assertEquals(d1.getFirstName(), savedDoctor.getFirstName());
        assertEquals(d1.getLastName(), savedDoctor.getLastName());
        assertEquals(d1.getAge(), savedDoctor.getAge());
        assertEquals(d1.getEmail(), savedDoctor.getEmail());
    }

    @Test
    void testDoctorSetId() {
        d1.setId(54321);
        assertEquals(54321, d1.getId());
    }

    @Test
    void testPatientEntity() {
        entityManager.persistAndFlush(p1);

        Patient savedPatient = entityManager.find(Patient.class, p1.getId());

        assertEquals(p1.getId(), savedPatient.getId());
        assertEquals(p1.getFirstName(), savedPatient.getFirstName());
        assertEquals(p1.getLastName(), savedPatient.getLastName());
        assertEquals(p1.getAge(), savedPatient.getAge());
        assertEquals(p1.getEmail(), savedPatient.getEmail());
    }

    @Test
    void testPatientSetId() {
        p1.setId(54321);
        assertEquals(54321, p1.getId());
    }

    @Test
    void testRoomEntity() {
        entityManager.persistAndFlush(r1);

        Room savedRoom = entityManager.find(Room.class, r1.getRoomName());

        assertEquals(r1.getRoomName(), savedRoom.getRoomName());

        Room r2 = new Room();
        assertNotNull(r2);
    }

    @Test
    void testAppointmentEntity() {
        a1 = entityManager.persistAndFlush(new Appointment(p1, d1, r1, startsAt, finishesAt));

        assertEquals(p1, a1.getPatient());
        assertEquals(d1, a1.getDoctor());
        assertEquals(r1, a1.getRoom());
        assertEquals(startsAt, a1.getStartsAt());
        assertEquals(finishesAt, a1.getFinishesAt());
    }

    @Test
    void testAppointmentSetMethods() {
        a1 = entityManager.persistAndFlush(new Appointment(p1, d1, r1, startsAt, finishesAt));

        a1.setId(54321);
        a1.setStartsAt(startsAt2);
        a1.setFinishesAt(finishesAt2);
        a1.setPatient(p2);
        a1.setDoctor(d2);
        a1.setRoom(r2);

        assertEquals(54321, a1.getId());
        assertEquals(startsAt2, a1.getStartsAt());
        assertEquals(finishesAt2, a1.getFinishesAt());
        assertEquals(p2, a1.getPatient());
        assertEquals(d2, a1.getDoctor());
        assertEquals(r2, a1.getRoom());
    }

    @Test
    void testOverlaps() {
        // Case 1: A.starts == B.starts
        a1 = entityManager.persistAndFlush(new Appointment(p1, d1, r1, startsAt, finishesAt));
        a2 = entityManager.persistAndFlush(new Appointment(p2, d2, r1, startsAt, finishesAt2));
        assertTrue(a1.overlaps(a2));

        // Case 2: A.finishes == B.finishes
        a3 = entityManager.persistAndFlush(new Appointment(p2, d2, r1, startsAt2, finishesAt));
        assertTrue(a1.overlaps(a3));

        // Case 3: A.starts < B.finishes && B.finishes < A.finishes
        a4 = entityManager.persistAndFlush(new Appointment(p2, d2, r1, startsAt3, finishesAt3));
        assertTrue(a1.overlaps(a4));

        // Case 4: B.starts < A.starts && A.finishes < B.finishes
        a5 = entityManager.persistAndFlush(new Appointment(p2, d2, r1, startsAt4, finishesAt4));
        assertTrue(a5.overlaps(a1));

        // Case 5: Different rooms
        a6 = entityManager.persistAndFlush(new Appointment(p2, d2, r2, startsAt, finishesAt));
        assertFalse(a1.overlaps(a6));
    }
}
