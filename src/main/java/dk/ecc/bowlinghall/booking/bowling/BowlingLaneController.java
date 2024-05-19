package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BowlingLaneController {

    private final BowlingLaneService bowlingLaneService;

    public BowlingLaneController(BowlingLaneService bowlingLaneService) {
        this.bowlingLaneService = bowlingLaneService;
    }

    @GetMapping("/lanes")
    public ResponseEntity<List<BowlingLaneDTO>> getBowlingLanes() {
        return ResponseEntity.ok(bowlingLaneService.getBowlingLaneDTOs());
    }

    @GetMapping("/bowling-lanes/availability")
    public ResponseEntity<Boolean> getBowlingLanesAvailability(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        boolean isAvailable = bowlingLaneService.isAvailable(start, end);
        return ResponseEntity.ok(isAvailable);
    }
}
