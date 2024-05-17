package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.stereotype.Service;

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

    public List<BowlingBookingDTO> getBowlingBookings() {
        List<BowlingBooking> bookings = bowlingBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public Optional<BowlingBookingDTO> getBowlingBooking(Long id) {
        return bowlingBookingRepository.findById(id).map(this::toDTO);
    }

    public List<BowlingBookingDTO> getBowlingBookingsByCustomerEmail(String customerEmail) {
        List<BowlingBooking> bookings = bowlingBookingRepository.findByCustomerEmail(customerEmail);
        return bookings.stream().map(this::toDTO).toList();
    }

    public List<BowlingBookingDTO> getBowlingBookingsWeekAhead() {
        LocalDateTime now = LocalDateTime.now();
        List<BowlingBooking> bookings = bowlingBookingRepository.findByStartAfter(now);
        return bookings.stream().filter(booking -> booking.getStart().isBefore(now.plusWeeks(1))).map(this::toDTO).toList();
    }

    public List<BowlingBookingDTO> getBowlingBookingsWeekAhead(String startDate) {
        LocalDateTime parsedStartDate = LocalDateTime.parse(startDate);
        List<BowlingBooking> bookings = bowlingBookingRepository.findByStartAfter(parsedStartDate);
        return bookings.stream().filter(booking -> booking.getStart().isBefore(parsedStartDate.plusWeeks(1))).map(this::toDTO).toList();
    }
}
