package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.airhockey.AirHockeyBooking;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTable;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTableRepository;
import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyBookingRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingBooking;
import dk.ecc.bowlinghall.booking.bowling.BowlingBookingRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingLane;
import dk.ecc.bowlinghall.booking.bowling.BowlingLaneRepository;
import dk.ecc.bowlinghall.booking.dinner.DinnerBookingDTO;
import dk.ecc.bowlinghall.booking.dinner.DinnerBookingRepository;
import dk.ecc.bowlinghall.booking.dinner.Restaurant;
import dk.ecc.bowlinghall.booking.dinner.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile("!test")
public class InitData implements CommandLineRunner {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final BowlingBookingRepository bowlingBookingRepository;
    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirHockeyTableRepository airHockeyTableRepository;
    private final RestaurantRepository restaurantRepository;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository, RestaurantRepository restaurantRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
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
        var today = LocalDateTime.now();
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        var booking1 = new BowlingBooking("email@test.t", today.withHour(10), today.withHour(11), bowlingLaneRepository.findById(1L).get());
        var booking2 = new BowlingBooking("email@test.t", today.withHour(11), today.withHour(12), bowlingLaneRepository.findById(2L).get());
        var booking3 = new BowlingBooking("email@test.t", today.withHour(12), today.withHour(13), bowlingLaneRepository.findById(3L).get());
        var booking4 = new BowlingBooking("email@test.t", today.withHour(13), today.withHour(14), bowlingLaneRepository.findById(4L).get());
        var booking5 = new BowlingBooking("email@test.t", today.withHour(14), today.withHour(15), bowlingLaneRepository.findById(5L).get());
        var booking6 = new BowlingBooking("email@test.t", today.withHour(15), today.withHour(16), bowlingLaneRepository.findById(6L).get());
        var booking7 = new BowlingBooking("email@test.t", today.withHour(16), today.withHour(17), bowlingLaneRepository.findById(7L).get());
        var booking8 = new BowlingBooking("email@test.t", today.withHour(17), today.withHour(18), bowlingLaneRepository.findById(8L).get());

        var booking9 = new BowlingBooking("email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), bowlingLaneRepository.findById(2L).get());
        var booking10 = new BowlingBooking("email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14), bowlingLaneRepository.findById(3L).get());
        var booking11 = new BowlingBooking("email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15), bowlingLaneRepository.findById(4L).get());
        var booking12 = new BowlingBooking("email@test.t", nextWeek.plusDays(3).withHour(15), nextWeek.plusDays(3).withHour(16), bowlingLaneRepository.findById(5L).get());
        var booking13 = new BowlingBooking("email@test.t", nextWeek.plusDays(4).withHour(16), nextWeek.plusDays(4).withHour(17), bowlingLaneRepository.findById(6L).get());
        var booking14 = new BowlingBooking("email@test.t", nextWeek.plusDays(5).withHour(17), nextWeek.plusDays(5).withHour(18), bowlingLaneRepository.findById(7L).get());
        var booking15 = new BowlingBooking("email@test.t", nextWeek.plusDays(6).withHour(18), nextWeek.plusDays(6).withHour(19), bowlingLaneRepository.findById(8L).get());
        var booking16 = new BowlingBooking("email@test.t", nextWeek.plusDays(7).withHour(13), nextWeek.plusDays(7).withHour(14), bowlingLaneRepository.findById(3L).get());
        var booking17 = new BowlingBooking("email@test.t", nextWeek.plusDays(8).withHour(14), nextWeek.plusDays(8).withHour(15), bowlingLaneRepository.findById(4L).get());
        var booking18 = new BowlingBooking("email@test.t", nextWeek.plusDays(9).withHour(15), nextWeek.plusDays(9).withHour(16), bowlingLaneRepository.findById(5L).get());
        var booking19 = new BowlingBooking("email@test.t", nextWeek.plusDays(10).withHour(16), nextWeek.plusDays(10).withHour(17), bowlingLaneRepository.findById(6L).get());
        var booking20 = new BowlingBooking("email@test.t", nextWeek.plusDays(11).withHour(17), nextWeek.plusDays(11).withHour(18), bowlingLaneRepository.findById(7L).get());
        var booking21 = new BowlingBooking("email@test.t", nextWeek.plusDays(12).withHour(18), nextWeek.plusDays(12).withHour(19), bowlingLaneRepository.findById(8L).get());


        bowlingBookingRepository.saveAll(
                Set.of(
                        booking1,
                        booking2,
                        booking3,
                        booking4,
                        booking5,
                        booking6,
                        booking7,
                        booking8,
                        booking9,
                        booking10,
                        booking11,
                        booking12,
                        booking13,
                        booking14,
                        booking15,
                        booking16,
                        booking17,
                        booking18,
                        booking19,
                        booking20,
                        booking21
                )
        );
    }

    private void createAirHockeyBookings() {
        var today = LocalDateTime.now();
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        var booking1 = new AirHockeyBooking("email@test.t", today.withHour(10), today.withHour(11), airHockeyTableRepository.findById(1L).get());
        var booking2 = new AirHockeyBooking("email@test.t", today.withHour(11), today.withHour(12), airHockeyTableRepository.findById(2L).get());
        var booking3 = new AirHockeyBooking("email@test.t", today.withHour(12), today.withHour(13), airHockeyTableRepository.findById(3L).get());
        var booking4 = new AirHockeyBooking("email@test.t", today.withHour(13), today.withHour(14), airHockeyTableRepository.findById(4L).get());
        var booking5 = new AirHockeyBooking("email@test.t", today.withHour(14), today.withHour(15), airHockeyTableRepository.findById(5L).get());
        var booking6 = new AirHockeyBooking("email@test.t", today.withHour(15), today.withHour(16), airHockeyTableRepository.findById(6L).get());
        var booking7 = new AirHockeyBooking("email@test.t", today.withHour(16), today.withHour(17), airHockeyTableRepository.findById(1L).get());
        var booking8 = new AirHockeyBooking("email@test.t", today.withHour(17), today.withHour(18), airHockeyTableRepository.findById(2L).get());

        var booking9 = new AirHockeyBooking("email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), airHockeyTableRepository.findById(3L).get());
        var booking10 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14), airHockeyTableRepository.findById(4L).get());
        var booking11 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15), airHockeyTableRepository.findById(5L).get());
        var booking12 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(3).withHour(15), nextWeek.plusDays(3).withHour(16), airHockeyTableRepository.findById(6L).get());
        var booking13 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(4).withHour(16), nextWeek.plusDays(4).withHour(17), airHockeyTableRepository.findById(1L).get());
        var booking14 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(5).withHour(17), nextWeek.plusDays(5).withHour(18), airHockeyTableRepository.findById(2L).get());
        var booking15 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(6).withHour(18), nextWeek.plusDays(6).withHour(19), airHockeyTableRepository.findById(3L).get());
        var booking16 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(7).withHour(13), nextWeek.plusDays(7).withHour(14), airHockeyTableRepository.findById(4L).get());
        var booking17 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(8).withHour(14), nextWeek.plusDays(8).withHour(15), airHockeyTableRepository.findById(5L).get());
        var booking18 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(9).withHour(15), nextWeek.plusDays(9).withHour(16), airHockeyTableRepository.findById(6L).get());
        var booking19 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(10).withHour(16), nextWeek.plusDays(10).withHour(17), airHockeyTableRepository.findById(1L).get());
        var booking20 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(11).withHour(17), nextWeek.plusDays(11).withHour(18), airHockeyTableRepository.findById(2L).get());
        var booking21 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(12).withHour(18), nextWeek.plusDays(12).withHour(19), airHockeyTableRepository.findById(3L).get());

        airHockeyBookingRepository.saveAll(
                Set.of(
                        booking1,
                        booking2,
                        booking3,
                        booking4,
                        booking5,
                        booking6,
                        booking7,
                        booking8,
                        booking9,
                        booking10,
                        booking11,
                        booking12,
                        booking13,
                        booking14,
                        booking15,
                        booking16,
                        booking17,
                        booking18,
                        booking19,
                        booking20,
                        booking21
                )
        );
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