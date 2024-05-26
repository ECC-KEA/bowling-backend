package dk.ecc.bowlinghall.admin.schedule;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {

    @Test
    void setStartValid() {
        var shift = new Shift();
        var start = LocalDateTime.now();
        var end = LocalDateTime.now().plusHours(2);

        shift.setStartAndEnd(start, end);

        assertEquals(start, shift.getStart());
    }

    @Test
    void setStartSameAsEnd() {
        var shift = new Shift();
        var start = LocalDateTime.now();
        var end = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> shift.setStartAndEnd(start, end));
    }

    @Test
    void setStartAfterEnd() {
        var shift = new Shift();
        var start = LocalDateTime.now().plusHours(2);
        var end = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> shift.setStartAndEnd(start, end));
    }

    @Test
    void setStartDifferentDay() {
        var shift = new Shift();
        var start = LocalDateTime.now().plusDays(1);
        var end = LocalDateTime.now().plusDays(2);

        assertThrows(IllegalArgumentException.class, () -> shift.setStartAndEnd(start, end));
    }

}