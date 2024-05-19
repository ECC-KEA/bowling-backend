package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.booking.airhockey.*;
import dk.ecc.bowlinghall.booking.bowling.*;
import dk.ecc.bowlinghall.booking.dinner.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@Component
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
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime initialTime = LocalTime.of(10, 0, 0);
        LocalDateTime initialDateTime = LocalDateTime.of(tomorrow, initialTime);

        List<BowlingBookingDTO> bookings = List.of(
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime, initialDateTime.plusHours(2), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(4), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(6), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(1), initialDateTime.plusHours(2), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(3), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(3), initialDateTime.plusHours(4), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(5), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(5), initialDateTime.plusHours(6), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime, initialDateTime.plusHours(2), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(4), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(6), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(1), initialDateTime.plusHours(2), null, true),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(3), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(3), initialDateTime.plusHours(4), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(5), null, false),
                new BowlingBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(5), initialDateTime.plusHours(6), null, false)


        );
        bookings.forEach(bowlingBookingService::addBowlingBooking);
    }

    private void createAirHockeyBookings() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime initialTime = LocalTime.of(10, 0, 0);
        LocalDateTime initialDateTime = LocalDateTime.of(tomorrow, initialTime);

        List<AirHockeyBookingDTO> bookings = List.of(
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime, initialDateTime.plusHours(2), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(4), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(6), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(8), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(1), initialDateTime.plusHours(2), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(3), null),
                new AirHockeyBookingDTO(null, null, "email@test.t", initialDateTime.plusHours(3), initialDateTime.plusHours(4), null)
        );
        bookings.forEach(airHockeyBookingService::addAirHockeyBooking);
    }

    private void createDinnerBookings() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime initialTime = LocalTime.of(10, 0, 0);
        LocalDateTime initialDateTime = LocalDateTime.of(tomorrow, initialTime);

        List<DinnerBookingDTO> bookings = List.of(
                new DinnerBookingDTO(null, "email@test.t", initialDateTime, initialDateTime.plusHours(2), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(4), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(6), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(8), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(1), initialDateTime.plusHours(2), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(2), initialDateTime.plusHours(3), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(3), initialDateTime.plusHours(4), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(4), initialDateTime.plusHours(5), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(5), initialDateTime.plusHours(6), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(7), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(7), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(7), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(8), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(8), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", initialDateTime.plusHours(6), initialDateTime.plusHours(8), Status.BOOKED, 2)
        );
        bookings.forEach(dinnerBookingService::create);
    }
}