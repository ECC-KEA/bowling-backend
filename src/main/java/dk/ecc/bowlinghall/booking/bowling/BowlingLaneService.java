package dk.ecc.bowlinghall.booking.bowling;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BowlingLaneService {

    private final BowlingLaneRepository bowlingLaneRepository;

    public BowlingLaneService(BowlingLaneRepository bowlingLaneRepository) {
        this.bowlingLaneRepository = bowlingLaneRepository;
    }

    public BowlingLaneDTO toDTO(BowlingLane lane) {
        return new BowlingLaneDTO(lane.getId(), lane.getPricePerHour(), lane.isChildFriendly());
    }

    public List<BowlingLaneDTO> getBowlingLaneDTOs() {
        return bowlingLaneRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BowlingLane> getBowlingLanes() {
        return bowlingLaneRepository.findAll();
    }

    public BowlingLane getBowlingLaneById(Long id) {
        return bowlingLaneRepository.findById(id).orElseThrow();
    }

    public BowlingLane saveBowlingLane(BowlingLane bowlingLane) {
        return bowlingLaneRepository.save(bowlingLane);
    }

    @Transactional
    public BowlingLane findFirstAvailableBowlingLane(LocalDateTime start, LocalDateTime end, boolean childFriendly) {
        var lanes = getBowlingLanes();
        return lanes.stream()
                .filter(lane -> lane.isAvailable(start, end) && lane.isChildFriendly() == childFriendly)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No lanes available"));
    }

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        return getBowlingLanes().stream().anyMatch(lane -> lane.isAvailable(start, end));
    }
}
