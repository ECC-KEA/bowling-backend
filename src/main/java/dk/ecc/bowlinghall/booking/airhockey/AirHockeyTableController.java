package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.time.LocalDateTime;


@RestController
public class AirHockeyTableController {

    private final AirHockeyTableService airHockeyTableService;

    public AirHockeyTableController(AirHockeyTableService airHockeyTableService) {
        this.airHockeyTableService = airHockeyTableService;
    }

    @GetMapping("/tables")
    public ResponseEntity<List<AirHockeyTableDTO>> getAirHockeyTables() {
        return ResponseEntity.ok(airHockeyTableService.getAirHockeyTableDTOs());
    }

    @GetMapping("/airhockey-tables/availability")
    public ResponseEntity<Boolean> getAirHockeyTableAvailability(LocalDateTime start, LocalDateTime end) {
        boolean isAvailable = airHockeyTableService.isAvailable(start, end);
        return ResponseEntity.ok(isAvailable);
    }
}
