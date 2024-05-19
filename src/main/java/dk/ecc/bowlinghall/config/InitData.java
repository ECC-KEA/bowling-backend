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
import dk.ecc.bowlinghall.booking.dinner.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
import java.time.LocalDateTime;
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
        if (bowlingBookingRepository.count() == 0) {
            createBowlingBookings();
        }
        if (airHockeyBookingRepository.count() == 0) {
            createAirHockeyBookings();
        }
        if (restaurantRepository.count() == 0) {
            createRestaurant();
        }
        if(dinnerBookingRepository.count() == 0){
            createDinnerBookings();
        }
    }



    private void createBowlingBookings() {
        var today = LocalDateTime.now();
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        var booking1 = new BowlingBooking("email@test.t", today.withHour(10), today.withHour(11),null);
        var booking2 = new BowlingBooking("email@test.t", today.withHour(11), today.withHour(12), null);
        var booking3 = new BowlingBooking("email@test.t", today.withHour(12), today.withHour(13), null);
        var booking4 = new BowlingBooking("email@test.t", today.withHour(13), today.withHour(14), null);
        var booking5 = new BowlingBooking("email@test.t", today.withHour(14), today.withHour(15), null);
        var booking6 = new BowlingBooking("email@test.t", today.withHour(15), today.withHour(16), null);
        var booking7 = new BowlingBooking("email@test.t", today.withHour(16), today.withHour(17), null);
        var booking8 = new BowlingBooking("email@test.t", today.withHour(17), today.withHour(18), null);
        var booking9 = new BowlingBooking("email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), null);
        var booking10 = new BowlingBooking("email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14), null);
        var booking11 = new BowlingBooking("email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15), null);
        var booking12 = new BowlingBooking("email@test.t", nextWeek.plusDays(3).withHour(15), nextWeek.plusDays(3).withHour(16), null);
        var booking13 = new BowlingBooking("email@test.t", nextWeek.plusDays(4).withHour(16), nextWeek.plusDays(4).withHour(17), null);
        var booking14 = new BowlingBooking("email@test.t", nextWeek.plusDays(5).withHour(17), nextWeek.plusDays(5).withHour(18), null);
        var booking15 = new BowlingBooking("email@test.t", nextWeek.plusDays(6).withHour(18), nextWeek.plusDays(6).withHour(19), null);
        var booking16 = new BowlingBooking("email@test.t", nextWeek.plusDays(7).withHour(13), nextWeek.plusDays(7).withHour(14), null);
        var booking17 = new BowlingBooking("email@test.t", nextWeek.plusDays(8).withHour(14), nextWeek.plusDays(8).withHour(15), null);
        var booking18 = new BowlingBooking("email@test.t", nextWeek.plusDays(9).withHour(15), nextWeek.plusDays(9).withHour(16), null);
        var booking19 = new BowlingBooking("email@test.t", nextWeek.plusDays(10).withHour(16), nextWeek.plusDays(10).withHour(17), null);
        var booking20 = new BowlingBooking("email@test.t", nextWeek.plusDays(11).withHour(17), nextWeek.plusDays(11).withHour(18), null);
        var booking21 = new BowlingBooking("email@test.t", nextWeek.plusDays(12).withHour(18), nextWeek.plusDays(12).withHour(19), null);

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

        booking1.setLane(bowlingLaneRepository.findById(1L).get());
        booking2.setLane(bowlingLaneRepository.findById(2L).get());
        booking3.setLane(bowlingLaneRepository.findById(3L).get());
        booking4.setLane(bowlingLaneRepository.findById(4L).get());
        booking5.setLane(bowlingLaneRepository.findById(5L).get());
        booking6.setLane(bowlingLaneRepository.findById(6L).get());
        booking7.setLane(bowlingLaneRepository.findById(7L).get());
        booking8.setLane(bowlingLaneRepository.findById(8L).get());
        booking9.setLane(bowlingLaneRepository.findById(9L).get());
        booking10.setLane(bowlingLaneRepository.findById(10L).get());
        booking11.setLane(bowlingLaneRepository.findById(11L).get());
        booking12.setLane(bowlingLaneRepository.findById(12L).get());
        booking13.setLane(bowlingLaneRepository.findById(13L).get());
        booking14.setLane(bowlingLaneRepository.findById(14L).get());
        booking15.setLane(bowlingLaneRepository.findById(15L).get());
        booking16.setLane(bowlingLaneRepository.findById(16L).get());
        booking17.setLane(bowlingLaneRepository.findById(17L).get());
        booking18.setLane(bowlingLaneRepository.findById(18L).get());
        booking19.setLane(bowlingLaneRepository.findById(19L).get());
        booking20.setLane(bowlingLaneRepository.findById(20L).get());
        booking21.setLane(bowlingLaneRepository.findById(21L).get());

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

        var booking1 = new AirHockeyBooking("email@test.t", today.withHour(10), today.withHour(11), null);
        var booking2 = new AirHockeyBooking("email@test.t", today.withHour(11), today.withHour(12), null);
        var booking3 = new AirHockeyBooking("email@test.t", today.withHour(12), today.withHour(13), null);
        var booking4 = new AirHockeyBooking("email@test.t", today.withHour(13), today.withHour(14), null);
        var booking5 = new AirHockeyBooking("email@test.t", today.withHour(14), today.withHour(15), null);
        var booking6 = new AirHockeyBooking("email@test.t", today.withHour(15), today.withHour(16), null);
        var booking7 = new AirHockeyBooking("email@test.t", today.withHour(16), today.withHour(17), null);
        var booking8 = new AirHockeyBooking("email@test.t", today.withHour(17), today.withHour(18), null);

        var booking9 = new AirHockeyBooking("email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), null);
        var booking10 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14), null);
        var booking11 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15), null);
        var booking12 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(3).withHour(15), nextWeek.plusDays(3).withHour(16), null);
        var booking13 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(4).withHour(16), nextWeek.plusDays(4).withHour(17), null);
        var booking14 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(5).withHour(17), nextWeek.plusDays(5).withHour(18), null);
        var booking15 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(6).withHour(18), nextWeek.plusDays(6).withHour(19), null);
        var booking16 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(7).withHour(13), nextWeek.plusDays(7).withHour(14), null);
        var booking17 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(8).withHour(14), nextWeek.plusDays(8).withHour(15), null);
        var booking18 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(9).withHour(15), nextWeek.plusDays(9).withHour(16), null);
        var booking19 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(10).withHour(16), nextWeek.plusDays(10).withHour(17), null);
        var booking20 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(11).withHour(17), nextWeek.plusDays(11).withHour(18), null);
        var booking21 = new AirHockeyBooking("email@test.t", nextWeek.plusDays(12).withHour(18), nextWeek.plusDays(12).withHour(19), null);

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

        booking1.setTable(airHockeyTableRepository.findById(1L).get());
        booking2.setTable(airHockeyTableRepository.findById(2L).get());
        booking3.setTable(airHockeyTableRepository.findById(3L).get());
        booking4.setTable(airHockeyTableRepository.findById(4L).get());
        booking5.setTable(airHockeyTableRepository.findById(5L).get());
        booking6.setTable(airHockeyTableRepository.findById(6L).get());
        booking7.setTable(airHockeyTableRepository.findById(1L).get());
        booking8.setTable(airHockeyTableRepository.findById(2L).get());
        booking9.setTable(airHockeyTableRepository.findById(3L).get());
        booking10.setTable(airHockeyTableRepository.findById(4L).get());
        booking11.setTable(airHockeyTableRepository.findById(5L).get());
        booking12.setTable(airHockeyTableRepository.findById(6L).get());
        booking13.setTable(airHockeyTableRepository.findById(1L).get());
        booking14.setTable(airHockeyTableRepository.findById(2L).get());
        booking15.setTable(airHockeyTableRepository.findById(3L).get());
        booking16.setTable(airHockeyTableRepository.findById(4L).get());
        booking17.setTable(airHockeyTableRepository.findById(5L).get());
        booking18.setTable(airHockeyTableRepository.findById(6L).get());
        booking19.setTable(airHockeyTableRepository.findById(1L).get());
        booking20.setTable(airHockeyTableRepository.findById(2L).get());
        booking21.setTable(airHockeyTableRepository.findById(3L).get());

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

    private void createRestaurant() {
        var restaurant = new Restaurant(100);
        restaurantRepository.save(restaurant);
    }

    private void createDinnerBookings() {
        var today = LocalDateTime.now();
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        var booking1 = new DinnerBooking(4, "email@test.t", today.withHour(10), today.withHour(11));
        var booking2 = new DinnerBooking(2, "email@test.t", today.withHour(12), today.withHour(13));
        var booking3 = new DinnerBooking(6, "email@test.t", today.withHour(13), today.withHour(14));
        var booking4 = new DinnerBooking(3, "email@test.t", today.withHour(14), today.withHour(15));
        var booking5 = new DinnerBooking(5, "email@test.t", today.withHour(14), today.withHour(15));
        var booking6 = new DinnerBooking(4, "email@test.t", today.withHour(15), today.withHour(16));
        var booking7 = new DinnerBooking(2, "email@test.t", today.withHour(16), today.withHour(17));
        var booking8 = new DinnerBooking(6, "email@test.t", today.withHour(17), today.withHour(18));
        var booking9 = new DinnerBooking(3, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13));
        var booking10 = new DinnerBooking(5, "email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14));
        var booking11 = new DinnerBooking(4, "email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15));
        var booking12 = new DinnerBooking(2, "email@test.t", nextWeek.plusDays(3).withHour(15), nextWeek.plusDays(3).withHour(16));
        var booking13 = new DinnerBooking(6, "email@test.t", nextWeek.plusDays(4).withHour(16), nextWeek.plusDays(4).withHour(17));
        var booking14 = new DinnerBooking(3, "email@test.t", nextWeek.plusDays(5).withHour(17), nextWeek.plusDays(5).withHour(18));
        var booking15 = new DinnerBooking(5, "email@test.t", nextWeek.plusDays(6).withHour(18), nextWeek.plusDays(6).withHour(19));
        var booking16 = new DinnerBooking(4, "email@test.t", nextWeek.plusDays(7).withHour(13), nextWeek.plusDays(7).withHour(14));
        var booking17 = new DinnerBooking(2, "email@test.t", nextWeek.plusDays(8).withHour(14), nextWeek.plusDays(8).withHour(15));
        var booking18 = new DinnerBooking(6, "email@test.t", nextWeek.plusDays(9).withHour(15), nextWeek.plusDays(9).withHour(16));
        var booking19 = new DinnerBooking(3, "email@test.t", nextWeek.plusDays(10).withHour(16), nextWeek.plusDays(10).withHour(17));
        var booking20 = new DinnerBooking(5, "email@test.t", nextWeek.plusDays(11).withHour(17), nextWeek.plusDays(11).withHour(18));
        var booking21 = new DinnerBooking(4, "email@test.t", nextWeek.plusDays(12).withHour(18), nextWeek.plusDays(12).withHour(19));

        dinnerBookingRepository.saveAll(
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
}