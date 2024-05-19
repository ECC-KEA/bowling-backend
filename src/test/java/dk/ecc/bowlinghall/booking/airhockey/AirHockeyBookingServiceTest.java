package dk.ecc.bowlinghall.booking.airhockey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class AirHockeyBookingServiceTest {

    @Autowired
    private AirHockeyBookingService airHockeyBookingService;

    @BeforeEach
    void setUp(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        airHockeyBookingRepository.deleteAll();
        var booking1 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                null
        );
        var booking2 = new AirHockeyBooking(
                "DONT SHOW ME",
                LocalDateTime.of(2025, 1, 8, 14, 0),
                LocalDateTime.of(2025, 1, 8, 16, 0),
                null
        );
        var booking3 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 7, 12, 0),
                LocalDateTime.of(2025, 1, 7, 14, 0),
                null
        );

        airHockeyBookingRepository.save(booking1);
        airHockeyBookingRepository.save(booking2);
        airHockeyBookingRepository.save(booking3);
    }

    @AfterEach
    void tearDown(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        airHockeyBookingRepository.deleteAll();
    }

    @Test
    void getBowlingBookingsPagination() {
        var bookings = airHockeyBookingService.getAirHockeyBookings(LocalDateTime.of(2025, 1, 1, 12, 0), 7);

        assertEquals(2, bookings.size());
        assertFalse(bookings.stream().anyMatch(booking -> booking.customerEmail().equals("DONT SHOW ME")));
    }
}