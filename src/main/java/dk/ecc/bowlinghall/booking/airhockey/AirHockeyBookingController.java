package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airhockey")
public class AirHockeyBookingController {

    private final AirHockeyBookingService airHockeyBookingService;

    public AirHockeyBookingController(AirHockeyBookingService airHockeyBookingService) {
        this.airHockeyBookingService = airHockeyBookingService;
    }

    @GetMapping
    public List<AirHockeyBookingDTO> getAirHockeyBookings() {
        return airHockeyBookingService.getAirHockeyBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirHockeyBookingDTO> getAirHockeyBooking(@PathVariable Long id) {
        return ResponseEntity.of(airHockeyBookingService.getAirHockeyBooking(id));
    }

    @GetMapping("/email/{customerEmail}")
    public List<AirHockeyBookingDTO> getAirHockeyBookingsByCustomerEmail(@PathVariable String customerEmail) {
        return airHockeyBookingService.getAirHockeyBookingsByCustomerEmail(customerEmail);
    }

}
