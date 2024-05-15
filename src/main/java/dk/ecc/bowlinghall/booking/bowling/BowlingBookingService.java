package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import org.springframework.stereotype.Service;

@Service
public class BowlingBookingService {

    private final BowlingBookingRepository bowlingBookingRepository;
    private final BowlingLaneService bowlingLaneService;

    public BowlingBookingService(BowlingBookingRepository bowlingBookingRepository, BowlingLaneService bowlingLaneService) {
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.bowlingLaneService = bowlingLaneService;
    }

    public BowlingBookingResponseDTO addBowlingBooking(BowlingBookingRequestDTO bowlingBookingRequestDTO) {
        var booking = toEntity(bowlingBookingRequestDTO);
        var savedBooking = bowlingBookingRepository.save(booking);

        var lane = savedBooking.getLane();
        lane.addBooking(savedBooking);
        bowlingLaneService.saveBowlingLane(lane);

        return toDTO(savedBooking);
    }

    private BowlingBookingResponseDTO toDTO(BowlingBooking booking) {
        return new BowlingBookingResponseDTO(
                booking.getId(),
                booking.getLane().getId(),
                booking.getCustomerEmail(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus()
        );
    }

    private BowlingBooking toEntity(BowlingBookingRequestDTO requestDTO) {
        return new BowlingBooking(
                requestDTO.customerEmail(),
                requestDTO.start(),
                requestDTO.end(),
                Status.BOOKED,
                bowlingLaneService.findFirstAvailableBowlingLane(requestDTO.start(), requestDTO.end(), requestDTO.childFriendly())
        );
    }
}
