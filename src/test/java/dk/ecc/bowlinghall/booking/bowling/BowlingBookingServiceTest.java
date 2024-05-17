package dk.ecc.bowlinghall.booking.bowling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class BowlingBookingServiceTest {

    @Autowired
    private BowlingBookingService bowlingBookingService;

    @BeforeEach
    void setUp(@Autowired BowlingBookingRepository bowlingBookingRepository) {
        bowlingBookingRepository.deleteAll();
        var booking1 = new BowlingBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                null
        );
        var booking2 = new BowlingBooking(
                "DONT SHOW ME",
                LocalDateTime.of(2025, 1, 8, 14, 0),
                LocalDateTime.of(2025, 1, 8, 16, 0),
                null
        );
        var booking3 = new BowlingBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 7, 12, 0),
                LocalDateTime.of(2025, 1, 7, 14, 0),
                null
        );

        bowlingBookingRepository.save(booking1);
        bowlingBookingRepository.save(booking2);
        bowlingBookingRepository.save(booking3);
    }

    @AfterEach
    void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DELETE FROM bowling_lane_bookings");
        jdbcTemplate.execute("DELETE FROM bowling_booking");
        jdbcTemplate.execute("DELETE FROM booking");
    }

    @Test
    void getBowlingBookingsPagination() {
        var bookings = bowlingBookingService.getBowlingBookings(LocalDate.of(2025, 1, 1), 7);

        assertEquals(2, bookings.size());
        assertFalse(bookings.stream().anyMatch(booking -> booking.customerEmail().equals("DONT SHOW ME")));
    }
}