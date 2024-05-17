package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DinnerBookingController {

    private final DinnerBookingService dinnerBookingService;

    public DinnerBookingController(DinnerBookingService dinnerBookingService) {
        this.dinnerBookingService = dinnerBookingService;
    }

    @GetMapping("/dinner")
    public List<DinnerBookingDTO> getDinnerBookings() {
        return dinnerBookingService.getDinnerBookings();
    }

    @GetMapping("/dinner/week")
    public List<DinnerBookingDTO> getDinnerBookingsWeekAhead() {
        return dinnerBookingService.getDinnerBookingsWeekAhead();
    }

    @GetMapping("/dinner/week/{startDate}")
    public List<DinnerBookingDTO> getDinnerBookingsWeekAhead(@PathVariable String startDate) {
        return dinnerBookingService.getDinnerBookingsWeekAhead(startDate);
    }

    @GetMapping("/dinner/{id}")
    public ResponseEntity<DinnerBookingDTO> getDinnerBooking(@PathVariable Long id) {
        return ResponseEntity.of(dinnerBookingService.getDinnerBooking(id));
    }

    @GetMapping("/dinner/email/{customerEmail}")
    public List<DinnerBookingDTO> getDinnerBookingsByEmail(@PathVariable String customerEmail) {
        return dinnerBookingService.getDinnerBookingsByEmail(customerEmail);
    }
}
