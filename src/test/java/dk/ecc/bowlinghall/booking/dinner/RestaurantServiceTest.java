package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    private Long id = 1L;

    @BeforeEach
    void setUp(@Autowired RestaurantRepository restaurantRepository, @Autowired DinnerBookingRepository dinnerBookingRepository) {
        var restaurant = new Restaurant(10);
        restaurantRepository.save(restaurant);

        var booking1 = new DinnerBooking(
                null,
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.BOOKED
        );
        var booking2 = new DinnerBooking(
                null,
                3,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 14, 0),
                LocalDateTime.of(2025, 1, 1, 15, 0),
                Status.BOOKED
        );
        var booking3 = new DinnerBooking(
                null,
                3,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 14, 0),
                LocalDateTime.of(2025, 1, 1, 15, 0),
                Status.BOOKED
        );
        var bookings = new ArrayList<>(List.of(booking1, booking2, booking3));
        var newBooking1 = dinnerBookingRepository.save(booking1);
        id = newBooking1.getId();
        dinnerBookingRepository.save(booking2);
        dinnerBookingRepository.save(booking3);

        restaurant.setBookings(bookings);
        restaurantRepository.save(restaurant);
    }

    @AfterEach
    void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DELETE FROM restaurant_bookings");
        jdbcTemplate.execute("DELETE FROM dinner_booking");
        jdbcTemplate.execute("DELETE FROM restaurant");
    }

    @Test
    void removeBooking() {
        var booking = new DinnerBooking(
                id,
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.BOOKED
        );

        restaurantService.removeBooking(booking);

        var restaurant = restaurantService.getRestaurant();
        assertEquals(2, restaurant.getBookings().size());
    }

    @Test
    void addBooking() {
        var booking = new DinnerBooking(
                100L,
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 16, 0),
                LocalDateTime.of(2025, 1, 1, 17, 0),
                Status.BOOKED
        );

        restaurantService.addBooking(booking);

        var restaurant = restaurantService.getRestaurant();
        assertEquals(4, restaurant.getBookings().size());
    }
}