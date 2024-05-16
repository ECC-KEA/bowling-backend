package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BowlingBookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    private final BowlingBookingDTO bowlingBookingDTO = new BowlingBookingDTO(
            null,
            null,
            "test@test.com",
            LocalDateTime.of(2024, 5, 20, 12, 0),
            LocalDateTime.of(2024, 5, 20, 14, 0),
            null,
            false);


    @Test
    void getAll() {
        var BOWLING_LANES = 24;
        var TIMESLOTS_PER_DAY = 10;
        var DAYS_PER_REQUEST = 7;
        var MAX_BOOKINGS = BOWLING_LANES * TIMESLOTS_PER_DAY * DAYS_PER_REQUEST;

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

    @Test
    void createBowlingBooking() {
        webClient.post().uri("/bowling")
                .bodyValue(bowlingBookingDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.laneId").exists()
                .jsonPath("$.customerEmail").isEqualTo("test@test.com")
                .jsonPath("$.start").isEqualTo("2024-05-20T12:00:00")
                .jsonPath("$.end").isEqualTo("2024-05-20T14:00:00")
                .jsonPath("$.status").isEqualTo("BOOKED")
                .jsonPath("$.childFriendly").isEqualTo(false);
    }

    @Test
    void updateBowlingBookingPartially() {
        var id = 1L;
        var body = new BowlingBookingDTO(
                null, null, null, null, null, Status.CANCELLED, null
        );
        webClient.patch().uri("/bowling/{id}", id)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BowlingBookingDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(id, response.id());
                    assertEquals(Status.CANCELLED, response.status());
                });
    }
}