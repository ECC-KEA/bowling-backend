package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BowlingBookingController {

    private final BowlingBookingService bowlingBookingService;

    public BowlingBookingController(BowlingBookingService bowlingBookingService) {
        this.bowlingBookingService = bowlingBookingService;
    }

    @PostMapping("/bowling")
    public ResponseEntity<BowlingBookingDTO> addBowlingBooking(@RequestBody BowlingBookingDTO bowlingBookingDTO) {
        var responseDTO = bowlingBookingService.addBowlingBooking(bowlingBookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @GetMapping("/bowling")
    public ResponseEntity<List<BowlingBookingDTO>> getBowlingBookings(
            @RequestParam(required = false) Integer day,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer limit
    ) {
        if(day == null) day = LocalDate.now().getDayOfMonth();
        if(month == null) month = LocalDate.now().getMonthValue();
        if(year == null) year = LocalDate.now().getYear();
        if(limit == null) limit = 7;
        return ResponseEntity.ok(bowlingBookingService.getBowlingBookings(LocalDateTime.of(year, month, day, 0, 0), limit));
    }

    @GetMapping("/bowling/{id}")
    public ResponseEntity<BowlingBookingDTO> getBowlingBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bowlingBookingService.getBowlingBooking(id));
    }

    @GetMapping("/bowling/email/{customerEmail}")
    public ResponseEntity<Page<BowlingBookingDTO>> getBowlingBookingsByCustomerEmail(
            @PathVariable String customerEmail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        System.out.println("getBowlingBookingsByCustomerEmail was called");
        return ResponseEntity.ok(bowlingBookingService.getBowlingBookingsByCustomerEmail(customerEmail, page, size));
    }

    @GetMapping("/bowling/{id}/price")
    public ResponseEntity<Double> getBowlingBookingPrice(@PathVariable Long id) {
        return ResponseEntity.ok(bowlingBookingService.getBowlingBookingPrice(id));
    }


    @PatchMapping("/bowling/{id}")
    public ResponseEntity<BowlingBookingDTO> updatePartialBowlingBooking(@PathVariable Long id, @RequestBody BowlingBookingDTO dto) {
        BowlingBookingDTO responseDTO = bowlingBookingService.updatePartialBowlingBooking(id, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
