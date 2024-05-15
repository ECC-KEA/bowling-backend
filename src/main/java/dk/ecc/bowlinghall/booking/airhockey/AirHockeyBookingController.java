package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
