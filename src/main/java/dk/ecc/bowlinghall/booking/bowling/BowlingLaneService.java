package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BowlingLaneService {

    private final BowlingLaneRepository bowlingLaneRepository;

    public BowlingLaneService(BowlingLaneRepository bowlingLaneRepository) {
        this.bowlingLaneRepository = bowlingLaneRepository;
    }

    public List<BowlingLane> getBowlingLanes() {
        return bowlingLaneRepository.findAll();
    }

    //TODO: Find out if this is needed
    public BowlingLane getBowlingLaneById(Long id) {
        return bowlingLaneRepository.findById(id).orElseThrow();
    }

    public BowlingLane saveBowlingLane(BowlingLane bowlingLane) {
        return bowlingLaneRepository.save(bowlingLane);
    }

    public BowlingLane findFirstAvailableBowlingLane(LocalDateTime start, LocalDateTime end, boolean childFriendly) {
        var lanes = getBowlingLanes();
        return lanes.stream()
                .filter(lane -> lane.isAvailable(start, end) && lane.isChildFriendly() == childFriendly)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No lanes available"));
    }
}
