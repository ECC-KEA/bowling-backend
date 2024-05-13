package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airhockey")
public class AirHockeyBookingController {

    private final AirHockeyBookingService airHockeyBookingService;

    public AirHockeyBookingController(AirHockeyBookingService airHockeyBookingService) {
        this.airHockeyBookingService = airHockeyBookingService;
    }

}
