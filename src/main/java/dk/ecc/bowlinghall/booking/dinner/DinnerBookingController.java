package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DinnerBookingController {

    private final DinnerBookingService dinnerBookingService;

    public DinnerBookingController(DinnerBookingService dinnerBookingService) {
        this.dinnerBookingService = dinnerBookingService;
    }

    @PostMapping("/dinner")
    public ResponseEntity<DinnerBookingDTO> addDinnerBooking(@RequestBody DinnerBookingDTO dinnerBookingDTO) {
        var responseDTO = dinnerBookingService.addDinnerBooking(dinnerBookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/dinner")
    public List<DinnerBookingDTO> getDinnerBookings() {
        return dinnerBookingService.getDinnerBookings();
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
