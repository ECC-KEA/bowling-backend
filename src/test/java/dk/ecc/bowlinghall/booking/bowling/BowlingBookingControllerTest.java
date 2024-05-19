package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
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
            LocalDateTime.now().plusDays(2).withHour(12),
            LocalDateTime.now().plusDays(2).withHour(13),
            null,
            false);

    private Long id;

    @BeforeEach
    void setUp(@Autowired BowlingBookingRepository bowlingBookingRepository, @Autowired BowlingLaneRepository bowlingLaneRepository) {
        var lane1 = new BowlingLane(200, true);
        var lane2 = new BowlingLane(200, false);
        var lane3 = new BowlingLane(200, false);
        var lane4 = new BowlingLane(200, false);

        bowlingLaneRepository.save(lane1);
        bowlingLaneRepository.save(lane2);
        bowlingLaneRepository.save(lane3);
        bowlingLaneRepository.save(lane4);
        var tomorrow = LocalDateTime.now().plusDays(1);
        var booking1 = new BowlingBooking(
                "SHOW ME",
                tomorrow.withHour(12),
                tomorrow.withHour(13),
                null
        );
        var booking2 = new BowlingBooking(
                "DONT SHOW ME",
                tomorrow.withHour(14),
                tomorrow.withHour(15),
                null
        );
        var booking3 = new BowlingBooking(
                "SHOW ME",
                tomorrow.withHour(16),
                tomorrow.withHour(17),
                null
        );

        var savedBooking = bowlingBookingRepository.save(booking1);
        bowlingBookingRepository.save(booking2);
        bowlingBookingRepository.save(booking3);

        id = savedBooking.getId();
    }

    @AfterEach
    void cleanUp(@Autowired BowlingBookingRepository bowlingBookingRepository, @Autowired BowlingLaneRepository bowlingLaneRepository) {
        bowlingBookingRepository.deleteAll();
        bowlingLaneRepository.deleteAll();
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
                .expectBody(BowlingBookingDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertNotNull(response.id());
                    assertEquals(bowlingBookingDTO.customerEmail(), response.customerEmail());
                    assertEquals(bowlingBookingDTO.start(), response.start());
                    assertEquals(bowlingBookingDTO.end(), response.end());
                });
    }

    @Test
    void updateBowlingBookingPartially() {
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