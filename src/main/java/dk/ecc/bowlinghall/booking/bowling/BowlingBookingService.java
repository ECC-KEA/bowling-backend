package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        System.out.println("service " + booking.getStart());
        var savedBooking = bowlingBookingRepository.save(booking);

        var lane = savedBooking.getLane();
        lane.addBooking(savedBooking);
        bowlingLaneService.saveBowlingLane(lane);
        System.out.println("saved booking " + savedBooking.getStart());
        return toDTO(savedBooking);
    }

    private BowlingBookingDTO toDTO(BowlingBooking booking) {
        boolean isChildFriendly = false;
        Long laneId = null;
        if(booking.getLane() != null) {
            isChildFriendly = booking.getLane().isChildFriendly();
            laneId = booking.getLane().getId();
        }
        return new BowlingBookingDTO(
                booking.getId(),
                laneId,
                booking.getCustomerEmail(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus(),
                isChildFriendly
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

    public List<BowlingBookingDTO> getBowlingBookings() {
        List<BowlingBooking> bookings = bowlingBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public List<BowlingBookingDTO> getBowlingBookings(LocalDateTime fromDate, int limit) {
        List<BowlingBooking> bookings = bowlingBookingRepository.findAllByStartBetween(fromDate, fromDate.plusDays(limit));
        return bookings.stream().map(this::toDTO).toList();
    }

    public BowlingBookingDTO getBowlingBooking(Long id) {
        return bowlingBookingRepository.findById(id).map(this::toDTO).orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    public List<BowlingBookingDTO> getBowlingBookingsByCustomerEmail(String customerEmail) {
        List<BowlingBooking> bookings = bowlingBookingRepository.findByCustomerEmail(customerEmail);
        return bookings.stream().map(this::toDTO).toList();
    }
}
