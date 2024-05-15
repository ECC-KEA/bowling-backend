package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.stereotype.Service;

@Service
public class BowlingBookingService {

    private final BowlingBookingRepository bowlingBookingRepository;
    private final BowlingLaneService bowlingLaneService;

    public BowlingBookingService(BowlingBookingRepository bowlingBookingRepository, BowlingLaneService bowlingLaneService) {
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.bowlingLaneService = bowlingLaneService;
    }

    public BowlingBookingDTO addBowlingBooking(BowlingBookingDTO bowlingBookingDTO) {
        var booking = toEntity(bowlingBookingDTO);
        var savedBooking = bowlingBookingRepository.save(booking);

        var lane = savedBooking.getLane();
        lane.addBooking(savedBooking);
        bowlingLaneService.saveBowlingLane(lane);

        return toDTO(savedBooking);
    }

    private BowlingBookingDTO toDTO(BowlingBooking booking) {
        return new BowlingBookingDTO(
                booking.getId(),
                booking.getLane().getId(),
                booking.getCustomerEmail(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus(),
                booking.getLane().isChildFriendly()
        );
    }

    private BowlingBooking toEntity(BowlingBookingDTO requestDTO) {
        return new BowlingBooking(
                requestDTO.customerEmail(),
                requestDTO.start(),
                requestDTO.end(),
                bowlingLaneService.findFirstAvailableBowlingLane(requestDTO.start(), requestDTO.end(), requestDTO.childFriendly())
        );
    }
}
