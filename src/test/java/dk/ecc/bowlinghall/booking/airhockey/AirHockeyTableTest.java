package dk.ecc.bowlinghall.booking.airhockey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AirHockeyTableTest {
    AirHockeyTable airHockeyTable;
    AirHockeyBooking mockBooking;

    @BeforeEach
    void setUp() {
        airHockeyTable = new AirHockeyTable(100.0);
        mockBooking = Mockito.mock(AirHockeyBooking.class);
    }

    @Test
    void addBookingAddsBookingToList() {
        airHockeyTable.addBooking(mockBooking);
        assertTrue(airHockeyTable.getBookings().contains(mockBooking));
    }

    @Test
    void removeBookingRemovesBookingFromList() {
        airHockeyTable.addBooking(mockBooking);
        airHockeyTable.removeBooking(mockBooking);
        assertFalse(airHockeyTable.getBookings().contains(mockBooking));
    }

    @Test
    void isAvailableReturnsTrueWhenNoBookings() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        assertTrue(airHockeyTable.isAvailable(start, end));
    }

    @Test
    void isAvailableReturnsFalseWhenBookingExists() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Mockito.when(mockBooking.getStart()).thenReturn(start);
        Mockito.when(mockBooking.getEnd()).thenReturn(end.plusHours(1));
        airHockeyTable.addBooking(mockBooking);
        assertFalse(airHockeyTable.isAvailable(start, end));
    }

    @Test
    void isAvailableReturnsTrueWhenBookingDoesNotOverlap() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Mockito.when(mockBooking.getStart()).thenReturn(start.plusHours(3));
        Mockito.when(mockBooking.getEnd()).thenReturn(end.plusHours(4));
        airHockeyTable.addBooking(mockBooking);
        assertTrue(airHockeyTable.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartAfterEnd() {
        LocalDateTime start = LocalDateTime.now().plusHours(2);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        assertThrows(IllegalArgumentException.class, () -> airHockeyTable.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartAndEndNotSameDay() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        assertThrows(IllegalArgumentException.class, () -> airHockeyTable.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartBeforeNow() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        assertThrows(IllegalArgumentException.class, () -> airHockeyTable.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenEndBeforeNow() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().minusHours(1);
        assertThrows(IllegalArgumentException.class, () -> airHockeyTable.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartAndEndAreSame() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now;
        LocalDateTime end = now;
        assertThrows(IllegalArgumentException.class, () -> airHockeyTable.isAvailable(start, end));
    }
}