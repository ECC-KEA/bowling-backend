package dk.ecc.bowlinghall.booking.dinner;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void getRemainingCapacityByTimeslotSameTimeSlot() {
        var restaurant = new Restaurant(10);
        var booking = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        restaurant.getBookings().add(booking);

        var remainingCapacity = restaurant.getRemainingCapacityByTimeslot(
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );

        assertEquals(5, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByTimeslotDifferentTimeSlot() {
        var restaurant = new Restaurant(10);
        var booking = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        restaurant.getBookings().add(booking);

        var remainingCapacity = restaurant.getRemainingCapacityByTimeslot(
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );

        assertEquals(10, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByTimeslotMultipleAdjacentBookings() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                3,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);

        var remainingCapacity = restaurant.getRemainingCapacityByTimeslot(
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );

        assertEquals(5, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByTimeslotFullyBooked() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);

        var remainingCapacity = restaurant.getRemainingCapacityByTimeslot(
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );

        assertEquals(0, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByStartAndEndMultipleHourSpan() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                3,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);

        var remainingCapacity = restaurant.getRemainingCapacityByStartAndEnd(
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );

        assertEquals(5, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByStartAndEndSingleHourSpan() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                3,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);

        var remainingCapacity = restaurant.getRemainingCapacityByStartAndEnd(
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );

        assertEquals(7, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByStartAndEndMultipleHourSpanOneFullyBooked() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                10,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);

        var remainingCapacity = restaurant.getRemainingCapacityByStartAndEnd(
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );

        assertEquals(0, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByStartAndEndMultipleHourSpanBothFullyBooked() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                10,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                10,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);

        var remainingCapacity = restaurant.getRemainingCapacityByStartAndEnd(
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );

        assertEquals(0, remainingCapacity);
    }

    @Test
    void getRemainingCapacityByStartAndEndManyHourSpan() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        var booking2 = new DinnerBooking(
                3,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0)
        );
        var booking3 = new DinnerBooking(
                2,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 14, 0),
                LocalDateTime.of(2025, 1, 1, 15, 0)
        );
        restaurant.getBookings().add(booking1);
        restaurant.getBookings().add(booking2);
        restaurant.getBookings().add(booking3);

        var remainingCapacity = restaurant.getRemainingCapacityByStartAndEnd(
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 20, 0)
        );

        assertEquals(5, remainingCapacity);
    }

}