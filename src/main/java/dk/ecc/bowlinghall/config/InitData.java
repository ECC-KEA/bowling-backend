package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.booking.airhockey.*;
import dk.ecc.bowlinghall.booking.bowling.*;
import dk.ecc.bowlinghall.booking.dinner.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;


@Component
@Profile("!test")
public class InitData implements CommandLineRunner {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final BowlingBookingRepository bowlingBookingRepository;
    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final BowlingBookingService bowlingBookingService;
    private final AirHockeyBookingService airHockeyBookingService;
    private final DinnerBookingService dinnerBookingService;
    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirHockeyTableRepository airHockeyTableRepository;
    private final RestaurantRepository restaurantRepository;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingBookingService bowlingBookingService, AirHockeyBookingService airHockeyBookingService, DinnerBookingService dinnerBookingService, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository, RestaurantRepository restaurantRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
        this.bowlingBookingService = bowlingBookingService;
        this.airHockeyBookingService = airHockeyBookingService;
        this.dinnerBookingService = dinnerBookingService;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airHockeyTableRepository = airHockeyTableRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bowlingLaneRepository.count() == 0) {
            IntStream.range(0, 24).mapToObj(i -> new BowlingLane(200, i < 4)).forEach(bowlingLaneRepository::save);
        }
        if (airHockeyTableRepository.count() == 0) {
            IntStream.range(0, 6).mapToObj(i -> new AirHockeyTable(50)).forEach(airHockeyTableRepository::save);
        }
        if (restaurantRepository.count() == 0) {
            restaurantRepository.save(new Restaurant(100));
        }
        if (bowlingBookingRepository.count() == 0) {
            createBowlingBookings();
        }
        if (airHockeyBookingRepository.count() == 0) {
            createAirHockeyBookings();
        }
        if (dinnerBookingRepository.count() == 0) {
            createDinnerBookings();
        }

    }

    private void createBowlingBookings() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        List<BowlingBookingDTO> bookings = List.of(
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(10), tomorrow.withHour(11), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(11), tomorrow.withHour(12), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(12), tomorrow.withHour(13), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(13), tomorrow.withHour(14), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(14), tomorrow.withHour(15), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(15), tomorrow.withHour(16), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(16), tomorrow.withHour(17), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(17), tomorrow.withHour(18), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(18), tomorrow.withHour(19), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(19), tomorrow.withHour(20), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(10), nextWeek.withHour(11), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(11), nextWeek.withHour(12), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(13), nextWeek.withHour(14), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(14), nextWeek.withHour(15), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(15), nextWeek.withHour(16), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(16), nextWeek.withHour(17), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(17), nextWeek.withHour(18), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(18), nextWeek.withHour(19), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(19), nextWeek.withHour(20), Status.BOOKED, false)
        );
        bookings.forEach(bowlingBookingService::addBowlingBooking);
    }

    private void createAirHockeyBookings() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        List<AirHockeyBookingDTO> bookings = List.of(
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(10), tomorrow.withHour(11), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(11), tomorrow.withHour(12), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(12), tomorrow.withHour(13), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(13), tomorrow.withHour(14), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(14), tomorrow.withHour(15), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(15), tomorrow.withHour(16), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(16), tomorrow.withHour(17), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(17), tomorrow.withHour(18), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(18), tomorrow.withHour(19), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(19), tomorrow.withHour(20), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(10), nextWeek.withHour(11), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(11), nextWeek.withHour(12), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(13), nextWeek.withHour(14), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(14), nextWeek.withHour(15), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(15), nextWeek.withHour(16), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(16), nextWeek.withHour(17), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(17), nextWeek.withHour(18), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(18), nextWeek.withHour(19), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(19), nextWeek.withHour(20), Status.BOOKED)
        );
        bookings.forEach(airHockeyBookingService::addAirHockeyBooking);
    }

    private void createDinnerBookings() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        List<DinnerBookingDTO> bookings = List.of(
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(10), tomorrow.withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(11), tomorrow.withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(12), tomorrow.withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(13), tomorrow.withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(14), tomorrow.withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(15), tomorrow.withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(16), tomorrow.withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(17), tomorrow.withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(18), tomorrow.withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(19), tomorrow.withHour(20), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(10), nextWeek.withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(11), nextWeek.withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(13), nextWeek.withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(14), nextWeek.withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(15), nextWeek.withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(16), nextWeek.withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(17), nextWeek.withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(18), nextWeek.withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(19), nextWeek.withHour(20), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(10), nextWeek.plusDays(1).withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(11), nextWeek.plusDays(1).withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(12), nextWeek.plusDays(1).withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(14), nextWeek.plusDays(1).withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(15), nextWeek.plusDays(1).withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(16), nextWeek.plusDays(1).withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(17), nextWeek.plusDays(1).withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(18), nextWeek.plusDays(1).withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(19), nextWeek.plusDays(1).withHour(20), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(10), nextWeek.plusDays(2).withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(11), nextWeek.plusDays(2).withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(12), nextWeek.plusDays(2).withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(13), nextWeek.plusDays(2).withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(15), nextWeek.plusDays(2).withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(16), nextWeek.plusDays(2).withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(17), nextWeek.plusDays(2).withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(18), nextWeek.plusDays(2).withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(19), nextWeek.plusDays(2).withHour(20), Status.BOOKED, 10)
        );
        bookings.forEach(dinnerBookingService::create);
    }
}