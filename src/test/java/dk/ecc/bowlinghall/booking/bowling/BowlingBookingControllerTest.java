package dk.ecc.bowlinghall.booking.bowling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BowlingBookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void getAll(){
        var BOWLING_LANES = 24;
        var TIMESLOTS_PER_DAY = 10;
        var DAYS_PER_REQUEST = 7;
        var MAX_BOOKINGS = BOWLING_LANES*TIMESLOTS_PER_DAY*DAYS_PER_REQUEST;

        webClient
                .get().uri("/bowling")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BowlingBookingDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertFalse(response.isEmpty());
                    assertTrue(response.size() <= MAX_BOOKINGS);
                });
    }

}