package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dinner")
public class DinnerBookingController {

    private final DinnerBookingService dinnerBookingService;

    public DinnerBookingController(DinnerBookingService dinnerBookingService) {
        this.dinnerBookingService = dinnerBookingService;
    }
}
