package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bowling")
public class BowlingBookingController {

    private final BowlingBookingService bowlingBookingService;

    public BowlingBookingController(BowlingBookingService bowlingBookingService) {
        this.bowlingBookingService = bowlingBookingService;
    }
}
