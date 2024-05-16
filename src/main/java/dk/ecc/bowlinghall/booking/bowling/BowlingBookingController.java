package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public List<BowlingBookingDTO> getBowlingBookings() {
        return bowlingBookingService.getBowlingBookings();
    }

    @GetMapping("/bowling/{id}")
    public ResponseEntity<BowlingBookingDTO> getBowlingBooking(@PathVariable Long id) {
        return ResponseEntity.of(bowlingBookingService.getBowlingBooking(id));
    }

    @GetMapping("/bowling/email/{customerEmail}")
    public List<BowlingBookingDTO> getBowlingBookingsByCustomerEmail(@PathVariable String customerEmail) {
        return bowlingBookingService.getBowlingBookingsByCustomerEmail(customerEmail);
    }

    @PatchMapping("/bowling/{id}")
    public ResponseEntity<BowlingBookingDTO> updatePartialBowlingBooking(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        BowlingBookingDTO responseDTO = bowlingBookingService.updatePartialBowlingBooking(id, fields);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
