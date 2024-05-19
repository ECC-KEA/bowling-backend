package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants/availability")
    public ResponseEntity<Boolean> getRestaurantAvailability(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam("numberOfGuests") int numberOfGuests) {
        boolean isAvailable = restaurantService.isAvailable(start, end, numberOfGuests);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<RestaurantDTO>> getRestaurant() {
        return ResponseEntity.ok(restaurantService.getRestaurants());
    }
}
