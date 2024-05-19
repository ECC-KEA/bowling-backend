package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class AirHockeyTableController {

    private final AirHockeyTableService airHockeyTableService;

    public AirHockeyTableController(AirHockeyTableService airHockeyTableService) {
        this.airHockeyTableService = airHockeyTableService;
    }

    @GetMapping("/airhockey-tables/availability")
    public ResponseEntity<Boolean> getAirHockeyTableAvailability(LocalDateTime start, LocalDateTime end) {
        boolean isAvailable = airHockeyTableService.isAvailable(start, end);
        return ResponseEntity.ok(isAvailable);
    }
}
