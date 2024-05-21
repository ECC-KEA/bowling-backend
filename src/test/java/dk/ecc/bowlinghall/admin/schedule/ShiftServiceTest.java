package dk.ecc.bowlinghall.admin.schedule;

import dk.ecc.bowlinghall.admin.employee.EmpType;
import dk.ecc.bowlinghall.admin.employee.Employee;
import dk.ecc.bowlinghall.admin.employee.EmployeeRepository;
import dk.ecc.bowlinghall.error.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ShiftServiceTest {

    @Autowired
    ShiftService shiftService;

    private Long id;
    private Long employeeId;

    @BeforeEach
    void setUp(@Autowired ShiftRepository shiftRepository, @Autowired EmployeeRepository employeeRepository){
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = tomorrow.plusWeeks(1);

        var shift1 = new Shift(null, tomorrow.withHour(8), tomorrow.withHour(16));
        var shift2 = new Shift(null, tomorrow.withHour(16), tomorrow.withHour(22));
        var shift3 = new Shift(null, nextWeek.withHour(8), nextWeek.withHour(16));
        var shift = shiftRepository.save(shift1);
        shiftRepository.save(shift2);
        shiftRepository.save(shift3);

        id = shift.getId();

        var employee = employeeRepository.save(new Employee(EmpType.REGULAR, "John", "Doe"));
        employeeId = employee.getId();
    }

    @AfterEach
    void tearDown(@Autowired ShiftRepository shiftRepository, @Autowired EmployeeRepository employeeRepository) {
        shiftRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getShift() {

    }

    @Test
    void getShiftsPagination() {
        var today = LocalDateTime.now();

        var shifts = shiftService.getShifts(today, 7);

        assertEquals(2, shifts.size());
    }

    @Test
    void createShift() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var shiftDTO = new ShiftDTO(null, employeeId, tomorrow.withHour(8), tomorrow.withHour(16));

        var shift = shiftService.createShift(shiftDTO);

        assertNotNull(shift.id());
        assertEquals(tomorrow.withHour(8), shift.start());
        assertEquals(tomorrow.withHour(16), shift.end());
        assertEquals(employeeId, shift.employeeId());
    }

    @Test
    void fromDTOInvalidEmployee() {
        var shiftDTO = new ShiftDTO(null, 0L, LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        assertThrows(NotFoundException.class, () -> shiftService.fromDTO(shiftDTO));
    }

    @Test
    void updateShift() {
        var nextWeek = LocalDateTime.now().plusWeeks(1);
        var updatedShiftDTO = new ShiftDTO(id, null, nextWeek.withHour(8), nextWeek.withHour(16));

        var updatedShift = shiftService.updateShift(id, updatedShiftDTO);

        assertEquals(id, updatedShift.id());
        assertEquals(nextWeek.withHour(8), updatedShift.start());
        assertEquals(nextWeek.withHour(16), updatedShift.end());
    }

    @Test
    void deleteShift() {
        shiftService.deleteShift(id);

        assertThrows(NotFoundException.class, () -> shiftService.getShift(id));
    }
}