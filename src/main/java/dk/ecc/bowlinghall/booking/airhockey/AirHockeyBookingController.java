package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AirHockeyBookingController {

    private final AirHockeyBookingService airHockeyBookingService;

    public AirHockeyBookingController(AirHockeyBookingService airHockeyBookingService) {
        this.airHockeyBookingService = airHockeyBookingService;
    }

    @GetMapping("/airhockey")
    public ResponseEntity<List<AirHockeyBookingDTO>> getAirHockeyBookings(
            @RequestParam(required = false) Integer day,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer limit
    ) {
        if(day == null) day = LocalDate.now().getDayOfMonth();
        if(month == null) month = LocalDate.now().getMonthValue();
        if(year == null) year = LocalDate.now().getYear();
        if(limit == null) limit = 7;
        return ResponseEntity.ok(airHockeyBookingService.getAirHockeyBookings(LocalDateTime.of(year, month, day, 0, 0), limit));
    }

    @GetMapping("/airhockey/{id}")
    public ResponseEntity<AirHockeyBookingDTO> getAirHockeyBooking(@PathVariable Long id) {
        return ResponseEntity.of(airHockeyBookingService.getAirHockeyBooking(id));
    }

    @GetMapping("/airhockey/email/{customerEmail}")
    public ResponseEntity<Page<AirHockeyBookingDTO>> getAirHockeyBookingsByCustomerEmail(
            @PathVariable String customerEmail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        System.out.println("getAirHockeyBookingsByCustomerEmail was called");
        return ResponseEntity.ok(airHockeyBookingService.getAirHockeyBookingsByCustomerEmail(customerEmail, page, size));
    }

    @PostMapping("/airhockey")
    public ResponseEntity<AirHockeyBookingDTO> addAirHockeyBooking(@RequestBody AirHockeyBookingDTO airHockeyBookingDTO) {
        var responseDTO = airHockeyBookingService.addAirHockeyBooking(airHockeyBookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PatchMapping("/airhockey/{id}")
    public ResponseEntity<AirHockeyBookingDTO> updatePartialAirHockeyBooking(@PathVariable Long id, @RequestBody AirHockeyBookingDTO dto) {
        AirHockeyBookingDTO responseDTO = airHockeyBookingService.updatePartialAirHockeyBooking(id, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
