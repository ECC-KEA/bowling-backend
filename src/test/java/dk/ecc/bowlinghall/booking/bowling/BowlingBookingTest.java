package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BowlingBookingTest {
    BowlingBooking bowlingBooking;
    BowlingLane mockLane;

    @BeforeEach
    void setUp() {
        mockLane = Mockito.mock(BowlingLane.class);
        bowlingBooking = new BowlingBooking("some@e.mail", LocalDateTime.now(), LocalDateTime.now().plusHours(1), mockLane);
    }

    @Test
    void cancelBowlingBooking() {
        bowlingBooking.setStatus(Status.CANCELLED);
        assertEquals(Status.CANCELLED, bowlingBooking.getStatus());
    }
}