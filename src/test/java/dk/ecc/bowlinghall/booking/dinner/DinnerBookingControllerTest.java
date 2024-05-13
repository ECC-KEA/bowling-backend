package dk.ecc.bowlinghall.booking.dinner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DinnerBookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void getAll(){
        var RESTAURANT_CAPACITY = 100;
        var MINIMUM_GUESTS = 2;
        var TIMESLOTS_PER_DAY = 10;
        var DAYS_PER_REQUEST = 7;
        var MAX_BOOKINGS = RESTAURANT_CAPACITY/MINIMUM_GUESTS*TIMESLOTS_PER_DAY*DAYS_PER_REQUEST;

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