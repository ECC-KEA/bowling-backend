package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bowling")
public class BowlingBookingController {

    private final BowlingBookingService bowlingBookingService;

    public BowlingBookingController(BowlingBookingService bowlingBookingService) {
        this.bowlingBookingService = bowlingBookingService;
    }

    @GetMapping
    public List<BowlingBookingDTO> getBowlingBookings() {
        return bowlingBookingService.getBowlingBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BowlingBookingDTO> getBowlingBooking(@PathVariable Long id) {
        return ResponseEntity.of(bowlingBookingService.getBowlingBooking(id));
    }

    @GetMapping("/email/{customerEmail}")
    public List<BowlingBookingDTO> getBowlingBookingsByCustomerEmail(@PathVariable String customerEmail) {
        return bowlingBookingService.getBowlingBookingsByCustomerEmail(customerEmail);
    }
}
