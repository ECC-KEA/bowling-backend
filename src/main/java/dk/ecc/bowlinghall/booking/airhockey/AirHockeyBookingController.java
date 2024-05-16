package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AirHockeyBookingController {

    private final AirHockeyBookingService airHockeyBookingService;

    public AirHockeyBookingController(AirHockeyBookingService airHockeyBookingService) {
        this.airHockeyBookingService = airHockeyBookingService;
    }

    @GetMapping("/airhockey")
    public List<AirHockeyBookingDTO> getAirHockeyBookings() {
        return airHockeyBookingService.getAirHockeyBookings();
    }

    @GetMapping("/airhockey/{id}")
    public ResponseEntity<AirHockeyBookingDTO> getAirHockeyBooking(@PathVariable Long id) {
        return ResponseEntity.of(airHockeyBookingService.getAirHockeyBooking(id));
    }

    @GetMapping("/airhockey/email/{customerEmail}")
    public List<AirHockeyBookingDTO> getAirHockeyBookingsByCustomerEmail(@PathVariable String customerEmail) {
        return airHockeyBookingService.getAirHockeyBookingsByCustomerEmail(customerEmail);
    }

    @PatchMapping("/airhockey/{id}")
    public ResponseEntity<AirHockeyBookingDTO> updatePartialAirHockeyBooking(@PathVariable Long id, @RequestBody AirHockeyBookingDTO dto) {
        AirHockeyBookingDTO responseDTO = airHockeyBookingService.updatePartialAirHockeyBooking(id, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
