package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.bowling.BowlingBooking;
import dk.ecc.bowlinghall.booking.bowling.BowlingBookingDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AirHockeyBookingService {

    private final AirHockeyBookingRepository airHockeyBookingRepository;

    public AirHockeyBookingService(AirHockeyBookingRepository airHockeyBookingRepository) {
        this.airHockeyBookingRepository = airHockeyBookingRepository;
    }

    private AirHockeyBookingDTO toDTO(AirHockeyBooking booking) {
        Long tableId = null;
        if(booking.getTable() != null) {
            tableId = booking.getTable().getId();
        }
        return new AirHockeyBookingDTO(
                booking.getId(),
                tableId,
                booking.getCustomerEmail(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus());
    }

    public List<AirHockeyBookingDTO> getAirHockeyBookings() {
        List<AirHockeyBooking> bookings = airHockeyBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public List<AirHockeyBookingDTO> getAirHockeyBookings(LocalDateTime fromDate, int limit) {
        List<AirHockeyBooking> bookings = airHockeyBookingRepository.findAllByStartBetween(fromDate, fromDate.plusDays(limit));
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
