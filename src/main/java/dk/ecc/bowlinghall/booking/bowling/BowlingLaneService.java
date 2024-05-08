package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.stereotype.Service;

@Service
public class BowlingLaneService {

    private final BowlingLaneRepository bowlingLaneRepository;

    public BowlingLaneService(BowlingLaneRepository bowlingLaneRepository) {
        this.bowlingLaneRepository = bowlingLaneRepository;
    }
}
