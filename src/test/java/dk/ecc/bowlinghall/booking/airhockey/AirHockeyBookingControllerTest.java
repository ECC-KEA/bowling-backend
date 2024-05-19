package dk.ecc.bowlinghall.booking.airhockey;

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
class AirHockeyBookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        var booking1 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(12),
                LocalDateTime.now().plusDays(1).withHour(13),
                null
        );
        var booking2 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(13),
                LocalDateTime.now().plusDays(1).withHour(14),
                null
        );
        var booking3 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(14),
                LocalDateTime.now().plusDays(1).withHour(15),
                null
        );

        airHockeyBookingRepository.save(booking1);
        airHockeyBookingRepository.save(booking2);
        airHockeyBookingRepository.save(booking3);
    }

    @AfterEach
    void cleanUp(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        airHockeyBookingRepository.deleteAll();
    }

    @Test
    void getAll() {
        var AIRHOCKEY_TABLES = 6;
        var TIMESLOTS_PER_DAY = 10;
        var DAYS_PER_REQUEST = 7;
        var MAX_BOOKINGS = AIRHOCKEY_TABLES * TIMESLOTS_PER_DAY * DAYS_PER_REQUEST;

        webClient
                .get().uri("/airhockey")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AirHockeyBookingDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertFalse(response.isEmpty());
                    assertTrue(response.size() <= MAX_BOOKINGS);
                });
    }

}