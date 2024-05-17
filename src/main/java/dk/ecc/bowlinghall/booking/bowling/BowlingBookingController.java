package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BowlingBookingController {

    private final BowlingBookingService bowlingBookingService;

    public BowlingBookingController(BowlingBookingService bowlingBookingService) {
        this.bowlingBookingService = bowlingBookingService;
    }

    @PostMapping("/bowling")
    public ResponseEntity<BowlingBookingDTO> addBowlingBooking(@RequestBody BowlingBookingDTO bowlingBookingDTO) {
        BowlingBookingDTO responseDTO = bowlingBookingService.addBowlingBooking(bowlingBookingDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @GetMapping("/bowling")
    public List<BowlingBookingDTO> getBowlingBookings(@RequestParam(required = false) LocalDate fromDate, @RequestParam(required = false) Integer days) {
        if(days == null) days = 7;
        if(fromDate != null) return bowlingBookingService.getBowlingBookings(fromDate, days);
        return bowlingBookingService.getBowlingBookings();
    }

    @GetMapping("/bowling/{id}")
    public ResponseEntity<BowlingBookingDTO> getBowlingBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bowlingBookingService.getBowlingBooking(id));
    }

    @GetMapping("/bowling/email/{customerEmail}")
    public ResponseEntity<List<BowlingBookingDTO>> getBowlingBookingsByCustomerEmail(@PathVariable String customerEmail) {
        return ResponseEntity.ok(bowlingBookingService.getBowlingBookingsByCustomerEmail(customerEmail));
    }
}
