package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.error.ValidationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DinnerBookingServiceTest {

    @Autowired
    private DinnerBookingService dinnerBookingService;

    private Long id = 1L;

    @BeforeEach
    void setUp(@Autowired RestaurantRepository restaurantRepository, @Autowired DinnerBookingRepository dinnerBookingRepository) {
        var restaurant = new Restaurant(10);
        restaurantRepository.save(restaurant);

        var booking = new DinnerBooking(
                null,
                5,
        "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.BOOKED
        );
        var bookings = new ArrayList<>(List.of(booking));
        var newBooking = dinnerBookingRepository.save(booking);
        id = newBooking.getId();

        restaurant.setBookings(bookings);
        restaurantRepository.save(restaurant);
    }

    @AfterEach
    void tearDown(@Autowired DinnerBookingRepository dinnerBookingRepository, @Autowired RestaurantRepository restaurantRepository) {
        restaurantRepository.deleteAll();
        dinnerBookingRepository.deleteAll();
    }

    @Test
    void get() {

    }

    @Test
    void create() {
        var newBooking = new DinnerBookingDTO(
                null,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.BOOKED,
                5
        );

        var createdBooking = dinnerBookingService.create(newBooking);

        assertNotNull(createdBooking.id());
        assertEquals(newBooking.customerEmail(), createdBooking.customerEmail());
        assertEquals(newBooking.start(), createdBooking.start());
        assertEquals(newBooking.end(), createdBooking.end());
        assertEquals(newBooking.status(), createdBooking.status());
        assertEquals(newBooking.numberOfGuests(), createdBooking.numberOfGuests());
    }

    @Test
    void createNotEnoughCapacityInRestaurant() {
        var newBooking = new DinnerBookingDTO(
                null,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.BOOKED,
                9
        );

        assertThrows(ValidationException.class, () -> dinnerBookingService.create(newBooking));
    }

    @Test
    void updateEnoughCapacity() {
        var updatedBooking = new DinnerBookingDTO(
                id,
                "updated@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.PAID,
                9
        );

        var createdBooking = dinnerBookingService.update(id, updatedBooking);

        assertEquals(updatedBooking.id(), createdBooking.id());
        assertEquals(updatedBooking.customerEmail(), createdBooking.customerEmail());
        assertEquals(updatedBooking.start(), createdBooking.start());
        assertEquals(updatedBooking.end(), createdBooking.end());
        assertEquals(updatedBooking.status(), createdBooking.status());
        assertEquals(updatedBooking.numberOfGuests(), createdBooking.numberOfGuests());
    }

    @Test
    void updateNotEnoughCapacity() {
        var updatedBooking = new DinnerBookingDTO(
                id,
                "updated@test.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.PAID,
                11
        );

        assertThrows(ValidationException.class, () -> dinnerBookingService.update(id, updatedBooking));
    }


    @Test
    void patch() {
            var updatedBooking = new DinnerBookingDTO(
                    id,
                    "updated@test.com",
                    null,
                    null,
                    Status.BOOKED,
                    3
            );

            var createdBooking = dinnerBookingService.patch(id, updatedBooking);

            assertEquals(updatedBooking.id(), createdBooking.id());
            assertEquals(updatedBooking.customerEmail(), createdBooking.customerEmail());
            assertEquals(LocalDateTime.of(2025, 1, 1, 13, 0), createdBooking.start());
            assertEquals(LocalDateTime.of(2025, 1, 1, 14, 0), createdBooking.end());
            assertEquals(updatedBooking.status(), createdBooking.status());
            assertEquals(updatedBooking.numberOfGuests(), createdBooking.numberOfGuests());
    }

    @Test
    void patchNotEnoughCapacity() {
        var updatedBooking = new DinnerBookingDTO(
                id,
                null,
                null,
                null,
                null,
                11
        );

        assertThrows(ValidationException.class, () -> dinnerBookingService.patch(id, updatedBooking));
    }

}