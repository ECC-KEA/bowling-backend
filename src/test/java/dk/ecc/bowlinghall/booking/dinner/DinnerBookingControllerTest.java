package dk.ecc.bowlinghall.booking.dinner;

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
class DinnerBookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    void setUp(@Autowired DinnerBookingRepository dinnerBookingRepository) {
        var booking1 = new DinnerBooking(
                3,
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(12),
                LocalDateTime.now().plusDays(1).withHour(13)
        );
        var booking2 = new DinnerBooking(
                3,
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(13),
                LocalDateTime.now().plusDays(1).withHour(14)
        );
        var booking3 = new DinnerBooking(
                3,
                "SHOW ME",
                LocalDateTime.now().plusDays(1).withHour(14),
                LocalDateTime.now().plusDays(1).withHour(15)
        );

        dinnerBookingRepository.save(booking1);
        dinnerBookingRepository.save(booking2);
        dinnerBookingRepository.save(booking3);
    }

    @AfterEach
    void cleanUp(@Autowired DinnerBookingRepository dinnerBookingRepository) {
        dinnerBookingRepository.deleteAll();
    }

    @Test
    void getAll() {
        var RESTAURANT_CAPACITY = 100;
        var MINIMUM_GUESTS = 2;
        var TIMESLOTS_PER_DAY = 10;
        var DAYS_PER_REQUEST = 7;
        var MAX_BOOKINGS = RESTAURANT_CAPACITY / MINIMUM_GUESTS * TIMESLOTS_PER_DAY * DAYS_PER_REQUEST;

        webClient
                .get().uri("/dinner?amountOfGuests=3")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DinnerBookingDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertFalse(response.isEmpty());
                    assertTrue(response.size() <= MAX_BOOKINGS);
                });
    }

}
