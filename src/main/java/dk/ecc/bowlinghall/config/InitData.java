/*package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyBookingRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingBooking;
import dk.ecc.bowlinghall.booking.bowling.BowlingBookingRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingLane;
import dk.ecc.bowlinghall.booking.bowling.BowlingLaneRepository;
import dk.ecc.bowlinghall.booking.dinner.DinnerBookingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitData implements CommandLineRunner {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final BowlingBookingRepository bowlingBookingRepository;
    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final BowlingLaneRepository bowlingLaneRepository;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingLaneRepository bowlingLaneRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createLanes();
        createBowlingBookings();
    }
    private void createLanes() {
        double pricePerHour = 100;
        boolean childFriendly = true;
        for(int i = 1; i <= 24; i++) {
            if(i > 4) {
                childFriendly = false;
            }
            BowlingLane lane = new BowlingLane(pricePerHour, childFriendly);
            bowlingLaneRepository.save(lane);
        }
    }

    private void createBowlingBookings() {
        Set<BowlingBooking> existingBookings = new HashSet<>();
        existingBookings.addAll(bowlingBookingRepository.findAll());

        BowlingBooking booking1 = new BowlingBooking("email@test.t", LocalDateTime.now(), LocalDateTime.now().plusHours(1), Status.PAID, bowlingLaneRepository.findById(1L).get());
        BowlingBooking booking2 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), Status.PAID, bowlingLaneRepository.findById(2L).get());
        BowlingBooking booking3 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5), Status.PAID, bowlingLaneRepository.findById(3L).get());
        BowlingBooking booking4 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(7), Status.PAID, bowlingLaneRepository.findById(4L).get());
        BowlingBooking booking5 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(8), LocalDateTime.now().plusHours(9), Status.PAID, bowlingLaneRepository.findById(5L).get());
        BowlingBooking booking6 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(11), Status.PAID, bowlingLaneRepository.findById(6L).get());
        BowlingBooking booking7 = new BowlingBooking("email@test.t", LocalDateTime.now().plusHours(12), LocalDateTime.now().plusHours(13), Status.PAID, bowlingLaneRepository.findById(7L).get());
        BowlingBooking booking8 = new BowlingBooking("otherEmail@test.t", LocalDateTime.now().plusHours(14), LocalDateTime.now().plusHours(15), Status.PAID, bowlingLaneRepository.findById(8L).get());

        existingBookings.addAll(Set.of(booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8));
        bowlingBookingRepository.saveAll(existingBookings);
    }
}
*/