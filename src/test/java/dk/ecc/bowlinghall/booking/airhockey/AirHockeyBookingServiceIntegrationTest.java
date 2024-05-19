package dk.ecc.bowlinghall.booking.airhockey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class AirHockeyBookingServiceIntegrationTest {

    @Autowired
    private AirHockeyBookingService airHockeyBookingService;

    @BeforeEach
    void setUp(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        airHockeyBookingRepository.deleteAll();
        var booking1 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(12),
                LocalDateTime.now().plusDays(1).withHour(13),
                null
        );
        var booking2 = new AirHockeyBooking(
                "DONT SHOW ME",
                LocalDateTime.now().plusWeeks(1).withHour(14),
                LocalDateTime.now().plusWeeks(1).withHour(15),
                null
        );
        var booking3 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(13),
                LocalDateTime.now().plusDays(1).withHour(14),
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
        var bookings = airHockeyBookingService.getAirHockeyBookings(LocalDateTime.now(), 7);

        assertEquals(3, bookings.size());
        assertFalse(bookings.stream().anyMatch(booking -> booking.customerEmail().equals("DONT SHOW ME")));
    }

}