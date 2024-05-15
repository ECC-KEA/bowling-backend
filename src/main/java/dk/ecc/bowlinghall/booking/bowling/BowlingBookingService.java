package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BowlingBookingService {

    private final BowlingBookingRepository bowlingBookingRepository;

    public BowlingBookingService(BowlingBookingRepository bowlingBookingRepository) {
        this.bowlingBookingRepository = bowlingBookingRepository;
    }

    private BowlingBookingDTO toDTO(BowlingBooking booking) {
        return new BowlingBookingDTO(
                booking.getId(),
                booking.getLane().getId(),
                booking.getCustomerEmail(),
                booking.getStart().toString(),
                booking.getEnd().toString(),
                booking.getStatus());
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
}
