package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
