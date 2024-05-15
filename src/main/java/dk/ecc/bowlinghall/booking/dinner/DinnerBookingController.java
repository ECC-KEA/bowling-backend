package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dinner")
public class DinnerBookingController {

    private final DinnerBookingService dinnerBookingService;

    public DinnerBookingController(DinnerBookingService dinnerBookingService) {
        this.dinnerBookingService = dinnerBookingService;
    }

    @GetMapping
    public List<DinnerBookingDTO> getDinnerBookings() {
        return dinnerBookingService.getDinnerBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DinnerBookingDTO> getDinnerBooking(@PathVariable Long id) {
        return ResponseEntity.of(dinnerBookingService.getDinnerBooking(id));
    }

    @GetMapping("/email/{customerEmail}")
    public List<DinnerBookingDTO> getDinnerBookingsByEmail(@PathVariable String customerEmail) {
        return dinnerBookingService.getDinnerBookingsByEmail(customerEmail);
    }
}
