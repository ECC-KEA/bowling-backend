package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.airhockey.*;
import dk.ecc.bowlinghall.booking.bowling.*;
import dk.ecc.bowlinghall.booking.dinner.DinnerBookingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class InitData implements CommandLineRunner {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final BowlingBookingRepository bowlingBookingRepository;
    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final BowlingBookingService bowlingBookingService;
    private final AirHockeyBookingService airHockeyBookingService;
    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirHockeyTableRepository airHockeyTableRepository;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingBookingService bowlingBookingService, AirHockeyBookingService airHockeyBookingService, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
        this.bowlingBookingService = bowlingBookingService;
        this.airHockeyBookingService = airHockeyBookingService;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airHockeyTableRepository = airHockeyTableRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        if (bowlingLaneRepository.count() == 0) {
            IntStream.range(0, 24).mapToObj(i -> new BowlingLane(200, i < 4)).forEach(bowlingLaneRepository::save);
        }
        if (airHockeyTableRepository.count() == 0) {
            IntStream.range(0, 6).mapToObj(i -> new AirHockeyTable(50)).forEach(airHockeyTableRepository::save);
        }
        if (bowlingBookingRepository.count() == 0) {
            createBowlingBookings();
        }
        if (airHockeyBookingRepository.count() == 0) {
            createAirHockeyBookings();
        }
    }

    private void createBowlingBookings() {
        List<BowlingBookingDTO> bookings = List.of(
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6), null, false)
        );
        bookings.forEach(bowlingBookingService::addBowlingBooking);
    }

    private void createAirHockeyBookings() {
        List<AirHockeyBookingDTO> bookings = List.of(
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6), null)
        );
        bookings.forEach(airHockeyBookingService::addAirHockeyBooking);
    }
}