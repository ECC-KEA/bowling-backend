package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BowlingBookingController {

    private final BowlingBookingService bowlingBookingService;

    public BowlingBookingController(BowlingBookingService bowlingBookingService) {
        this.bowlingBookingService = bowlingBookingService;
    }

    @PostMapping("/bowling")
    public ResponseEntity<BowlingBookingResponseDTO> addBowlingBooking(@RequestBody BowlingBookingRequestDTO bowlingBookingRequestDTO) {
        BowlingBookingResponseDTO responseDTO = bowlingBookingService.addBowlingBooking(bowlingBookingRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

}
