package dk.ecc.bowlinghall.admin.schedule;

import dk.ecc.bowlinghall.admin.employee.EmpType;
import dk.ecc.bowlinghall.admin.employee.Employee;
import dk.ecc.bowlinghall.admin.employee.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ScheduleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private Long id;
    private Long employeeId;
    private final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
    private final LocalDateTime nextWeek = tomorrow.plusWeeks(1);

    @BeforeEach
    void setUp(@Autowired ShiftRepository shiftRepository, @Autowired EmployeeRepository employeeRepository) {
        var employee = employeeRepository.save(new Employee(EmpType.REGULAR, "John", "Doe"));
        employeeId = employee.getId();

        var shift1 = new Shift(null, tomorrow.withHour(8), tomorrow.withHour(16));
        var shift2 = new Shift(null, tomorrow.withHour(16), tomorrow.withHour(22));
        var shift3 = new Shift(null, nextWeek.withHour(8), nextWeek.withHour(16));

        var shift = shiftRepository.save(shift1);
        shiftRepository.save(shift2);
        shiftRepository.save(shift3);

        id = shift.getId();
    }

    @AfterEach
    void tearDown(@Autowired ShiftRepository shiftRepository, @Autowired EmployeeRepository employeeRepository) {
        shiftRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void patchShiftWithStartOnly() {
        var shiftDTO = new ShiftDTO(null, null, tomorrow.withHour(10), null);
        var response = webTestClient.patch()
                .uri("/schedule/" + id)
                .bodyValue(shiftDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShiftDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(tomorrow.withHour(10), response.start());
        assertNotNull(response.end());
    }

    @Test
    void patchShiftWithEndOnly() {
        var shiftDTO = new ShiftDTO(null, null, null, tomorrow.withHour(18));
        var response = webTestClient.patch()
                .uri("/schedule/" + id)
                .bodyValue(shiftDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShiftDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(tomorrow.withHour(18), response.end());
        assertNotNull(response.start());
    }

    @Test
    void createShift() {
        var shiftDTO = new ShiftDTO(null, employeeId, tomorrow.withHour(8), tomorrow.withHour(16));
        var response = webTestClient.post()
                .uri("/schedule")
                .bodyValue(shiftDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShiftDTO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(tomorrow.withHour(8), response.start());
        assertEquals(tomorrow.withHour(16), response.end());
        assertEquals(employeeId, response.employeeId());
    }
}