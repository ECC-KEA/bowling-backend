package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.error.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DinnerBookingServiceTest {

    @InjectMocks
    private DinnerBookingService dinnerBookingService;

    @Mock
    private DinnerBookingRepository dinnerBookingRepository;

    @Mock
    private RestaurantService restaurantService;

    @Test
    void get() {
        var booking = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        when(dinnerBookingRepository.findById(1L)).thenReturn(java.util.Optional.of(booking));

        var result = dinnerBookingService.get(1L);

        assertEquals(result.customerEmail(), "test@test.com");
        assertEquals(result.numberOfGuests(), 5);
        assertEquals(result.start(), LocalDateTime.of(2025, 1, 1, 12, 0));
        assertEquals(result.end(), LocalDateTime.of(2025, 1, 1, 13, 0));
    }

    @Test
    void create() {
        var restaurant = new Restaurant(10);
        when(restaurantService.getRestaurant()).thenReturn(restaurant);
        var booking = new DinnerBookingDTO(
                null,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0),
                null,
                3);

        var result = dinnerBookingService.create(booking);

        assertEquals(result.customerEmail(), "test@test.com");
        assertEquals(result.numberOfGuests(), 3);
        assertEquals(result.start(), LocalDateTime.of(2025, 1, 1, 12, 0));
        assertEquals(result.end(), LocalDateTime.of(2025, 1, 1, 13, 0));
    }

    @Test
    void createFullyBooked() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                10,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        restaurant.addBooking(booking1);
        when(restaurantService.getRestaurant()).thenReturn(restaurant);

        var booking2 = new DinnerBookingDTO(
                null,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0),
                null,
                1);

        assertThrows(ValidationException.class, () -> dinnerBookingService.create(booking2));
    }

    @Test
    void update() {
        var restaurant = new Restaurant(10);
        when(restaurantService.getRestaurant()).thenReturn(restaurant);
        var booking = new DinnerBooking(
                1L,
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0),
                Status.BOOKED
        );
        when(dinnerBookingRepository.findById(1L)).thenReturn(java.util.Optional.of(booking));

        var updateBooking = new DinnerBookingDTO(
                1L,
                "updated@updated.com",
                LocalDateTime.of(2025, 1, 1, 13, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                Status.PAID,
                3);

        var result = dinnerBookingService.update(1L, updateBooking);

        assertEquals("updated@updated.com", result.customerEmail());
        assertEquals(3, result.numberOfGuests());
        assertEquals(LocalDateTime.of(2025, 1, 1, 13, 0), result.start());
        assertEquals(LocalDateTime.of(2025, 1, 1, 14, 0), result.end());
        assertEquals(Status.PAID, result.status());
    }

    @Test
    void patch() {

    }

    @Test
    void addToRestaurantNoPriorBookings() {
        var restaurant = new Restaurant(10);
        when(restaurantService.getRestaurant()).thenReturn(restaurant);

        var booking = new DinnerBooking(
                5,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );

        dinnerBookingService.addToRestaurant(booking);

        assertEquals(1, restaurant.getBookings().size());
    }

    @Test
    void addToRestaurantFullyBooked() {
        var restaurant = new Restaurant(10);
        var booking1 = new DinnerBooking(
                10,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );
        restaurant.addBooking(booking1);
        when(restaurantService.getRestaurant()).thenReturn(restaurant);

        var booking2 = new DinnerBooking(
                1,
                "test@test.com",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 13, 0)
        );

        assertThrows(ValidationException.class, () -> dinnerBookingService.addToRestaurant(booking2));
        assertEquals(1, restaurant.getBookings().size());
    }
}