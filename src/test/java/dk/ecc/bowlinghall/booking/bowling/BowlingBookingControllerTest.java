package dk.ecc.bowlinghall.booking.bowling;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BowlingBookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    private final BowlingBookingRequestDTO bowlingBookingRequestDTO = new BowlingBookingRequestDTO(LocalDateTime.of(2024, 5, 20, 12, 0), LocalDateTime.of(2024, 5, 20, 14, 0), "test@test.com", false);

    @AfterAll
    static void cleanUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DELETE FROM bowling_lane_bookings");
        jdbcTemplate.execute("DELETE FROM bowling_booking");
        jdbcTemplate.execute("DELETE FROM booking");
    }

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
                .expectBodyList(BowlingBookingResponseDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertFalse(response.isEmpty());
                    assertTrue(response.size() <= MAX_BOOKINGS);
                });
    }

    @Test
    void createBowlingBooking() {
        webClient.post().uri("/bowling")
                .bodyValue(bowlingBookingRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.laneId").exists()
                .jsonPath("$.customerEmail").isEqualTo("test@test.com")
                .jsonPath("$.start").isEqualTo("2024-05-20T12:00:00")
                .jsonPath("$.end").isEqualTo("2024-05-20T14:00:00")
                .jsonPath("$.status").isEqualTo("BOOKED");
    }
}