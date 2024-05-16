package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.airhockey.AirHockeyBooking;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyBookingRepository;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTable;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTableRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingBooking;
import dk.ecc.bowlinghall.booking.bowling.BowlingBookingRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingLane;
import dk.ecc.bowlinghall.booking.bowling.BowlingLaneRepository;
import dk.ecc.bowlinghall.booking.dinner.DinnerBookingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class InitData implements CommandLineRunner {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final BowlingBookingRepository bowlingBookingRepository;
    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirHockeyTableRepository airHockeyTableRepository;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
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
        BowlingBooking booking1 = new BowlingBooking("email@test.t", LocalDateTime.now(), LocalDateTime.now().plusHours(1), bowlingLaneRepository.findById(1L).get());
        BowlingBooking booking2 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), bowlingLaneRepository.findById(2L).get());
        BowlingBooking booking3 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), bowlingLaneRepository.findById(3L).get());
        BowlingBooking booking4 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), bowlingLaneRepository.findById(4L).get());
        BowlingBooking booking5 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), bowlingLaneRepository.findById(5L).get());
        BowlingBooking booking6 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6), bowlingLaneRepository.findById(6L).get());
        BowlingBooking booking7 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(7), bowlingLaneRepository.findById(7L).get());
        BowlingBooking booking8 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(8), bowlingLaneRepository.findById(8L).get());

        bowlingBookingRepository.saveAll(Set.of(booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8));
    }

    private void createAirHockeyBookings() {
        AirHockeyBooking booking1 = new AirHockeyBooking("email@test.t", LocalDateTime.now(), LocalDateTime.now().plusHours(1), airHockeyTableRepository.findById(1L).get());
        AirHockeyBooking booking2 = new AirHockeyBooking("email@test.t", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), airHockeyTableRepository.findById(2L).get());
        AirHockeyBooking booking3 = new AirHockeyBooking("email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), airHockeyTableRepository.findById(3L).get());
        AirHockeyBooking booking4 = new AirHockeyBooking("email@test.t", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), airHockeyTableRepository.findById(4L).get());
        AirHockeyBooking booking5 = new AirHockeyBooking("email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), airHockeyTableRepository.findById(5L).get());
        AirHockeyBooking booking6 = new AirHockeyBooking("email@test.t", LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6), airHockeyTableRepository.findById(6L).get());

        airHockeyBookingRepository.saveAll(Set.of(booking1, booking2, booking3, booking4, booking5, booking6));
    }
}