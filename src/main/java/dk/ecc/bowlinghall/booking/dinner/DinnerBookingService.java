package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DinnerBookingService {

    private final DinnerBookingRepository dinnerBookingRepository;

    public DinnerBookingService(DinnerBookingRepository dinnerBookingRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
    }

    private DinnerBookingDTO toDTO(DinnerBooking dinnerBooking) {
        return new DinnerBookingDTO(
                dinnerBooking.getId(),
                dinnerBooking.getCustomerEmail(),
                dinnerBooking.getStart(),
                dinnerBooking.getEnd(),
                dinnerBooking.getStatus(),
                dinnerBooking.getNumberOfGuests());
    }

    public List<DinnerBookingDTO> getDinnerBookings() {
        List<DinnerBooking> bookings = dinnerBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public Optional<DinnerBookingDTO> getDinnerBooking(Long id) {
        return dinnerBookingRepository.findById(id).map(this::toDTO);
    }

    public List<DinnerBookingDTO> getDinnerBookingsByEmail(String customerEmail) {
        List<DinnerBooking> bookings = dinnerBookingRepository.findByCustomerEmail(customerEmail);
        return bookings.stream().map(this::toDTO).toList();
    }
}
