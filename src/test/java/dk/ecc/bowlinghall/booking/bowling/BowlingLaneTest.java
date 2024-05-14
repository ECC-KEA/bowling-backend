package dk.ecc.bowlinghall.booking.bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BowlingLaneTest {
    BowlingLane bowlingLane;
    BowlingBooking mockBooking;

    @BeforeEach
    void setUp() {
        bowlingLane = new BowlingLane(100.0, true);
        mockBooking = Mockito.mock(BowlingBooking.class);
    }

    @Test
    void addBookingAddsBookingToList() {
        bowlingLane.addBooking(mockBooking);
        assertTrue(bowlingLane.getBookings().contains(mockBooking));
    }

    @Test
    void removeBookingRemovesBookingFromList() {
        bowlingLane.addBooking(mockBooking);
        bowlingLane.removeBooking(mockBooking);
        assertFalse(bowlingLane.getBookings().contains(mockBooking));
    }

    @Test
    void isAvailableReturnsTrueWhenNoBookings() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        assertTrue(bowlingLane.isAvailable(start, end));
    }

    @Test
    void isAvailableReturnsFalseWhenBookingExists() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Mockito.when(mockBooking.getStart()).thenReturn(start);
        Mockito.when(mockBooking.getEnd()).thenReturn(end.plusHours(1));
        bowlingLane.addBooking(mockBooking);
        assertFalse(bowlingLane.isAvailable(start, end));
    }

    @Test
    void isAvailableReturnsTrueWhenBookingDoesNotOverlap() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Mockito.when(mockBooking.getStart()).thenReturn(start.plusHours(3));
        Mockito.when(mockBooking.getEnd()).thenReturn(end.plusHours(4));
        bowlingLane.addBooking(mockBooking);
        assertTrue(bowlingLane.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartAfterEnd() {
        LocalDateTime start = LocalDateTime.now().plusHours(2);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        assertThrows(IllegalArgumentException.class, () -> bowlingLane.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartAndEndNotSameDay() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        assertThrows(IllegalArgumentException.class, () -> bowlingLane.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartBeforeNow() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        assertThrows(IllegalArgumentException.class, () -> bowlingLane.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenEndBeforeNow() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().minusHours(1);
        assertThrows(IllegalArgumentException.class, () -> bowlingLane.isAvailable(start, end));
    }

    @Test
    void validateTimesThrowsExceptionWhenStartAndEndAreSame() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now;
        LocalDateTime end = now;
        assertThrows(IllegalArgumentException.class, () -> bowlingLane.isAvailable(start, end));
    }
}