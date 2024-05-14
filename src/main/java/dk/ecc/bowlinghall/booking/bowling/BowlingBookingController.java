package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BowlingBookingController {

    private final BowlingBookingService bowlingBookingService;

    public BowlingBookingController(BowlingBookingService bowlingBookingService) {
        this.bowlingBookingService = bowlingBookingService;
    }

}
