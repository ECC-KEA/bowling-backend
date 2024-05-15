package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirHockeyBookingService {

    private final AirHockeyBookingRepository airHockeyBookingRepository;

    public AirHockeyBookingService(AirHockeyBookingRepository airHockeyBookingRepository) {
        this.airHockeyBookingRepository = airHockeyBookingRepository;
    }

    private AirHockeyBookingDTO toDTO(AirHockeyBooking booking) {
        return new AirHockeyBookingDTO(
                booking.getId(),
                booking.getTable().getId(),
                booking.getCustomerEmail(),
                booking.getStart().toString(),
                booking.getEnd().toString(),
                booking.getStatus());
    }

    public List<AirHockeyBookingDTO> getAirHockeyBookings() {
        List<AirHockeyBooking> bookings = airHockeyBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public Optional<AirHockeyBookingDTO> getAirHockeyBooking(Long id) {
        return airHockeyBookingRepository.findById(id).map(this::toDTO);
    }

    public List<AirHockeyBookingDTO> getAirHockeyBookingsByCustomerEmail(String customerEmail) {
        List<AirHockeyBooking> bookings = airHockeyBookingRepository.findByCustomerEmail(customerEmail);
        return bookings.stream().map(this::toDTO).toList();
    }
}
