package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DinnerBookingService {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final RestaurantService restaurantService;

    public DinnerBookingService(DinnerBookingRepository dinnerBookingRepository, RestaurantService restaurantService) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.restaurantService = restaurantService;
    }

    public DinnerBooking fromDTO(DinnerBookingDTO dinnerBookingDTO) {
        return new DinnerBooking(
                dinnerBookingDTO.numberOfGuests(),
                dinnerBookingDTO.customerEmail(),
                dinnerBookingDTO.start(),
                dinnerBookingDTO.end()
        );
    }

    public DinnerBookingDTO get(Long id) {
        return dinnerBookingRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    public DinnerBookingDTO create(DinnerBookingDTO dinnerBookingDTO) {
        DinnerBooking dinnerBooking = fromDTO(dinnerBookingDTO);
        addToRestaurant(dinnerBooking);
        dinnerBookingRepository.save(dinnerBooking);
        return toDTO(dinnerBooking);
    }

    public DinnerBookingDTO update(Long id, DinnerBookingDTO dinnerBookingDTO) {
        var dinnerBooking = dinnerBookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        // Remove booking and try to add it again after updating values
        restaurantService.removeBooking(dinnerBooking);

        dinnerBooking.setCustomerEmail(dinnerBookingDTO.customerEmail());
        dinnerBooking.setStart(dinnerBookingDTO.start());
        dinnerBooking.setEnd(dinnerBookingDTO.end());
        dinnerBooking.setNumberOfGuests(dinnerBookingDTO.numberOfGuests());
        dinnerBooking.setStatus(dinnerBookingDTO.status());

        addToRestaurant(dinnerBooking);

        dinnerBookingRepository.save(dinnerBooking);
        return toDTO(dinnerBooking);
    }

    public DinnerBookingDTO patch(Long id, DinnerBookingDTO dinnerBookingDTO) {
        var dinnerBooking = dinnerBookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        // Remove booking and try to add it again after updating values
        restaurantService.removeBooking(dinnerBooking);

        if (dinnerBookingDTO.customerEmail() != null) {
            dinnerBooking.setCustomerEmail(dinnerBookingDTO.customerEmail());
        }
        if (dinnerBookingDTO.start() != null) {
            dinnerBooking.setStart(dinnerBookingDTO.start());
        }
        if (dinnerBookingDTO.end() != null) {
            dinnerBooking.setEnd(dinnerBookingDTO.end());
        }
        if (dinnerBookingDTO.numberOfGuests() != null) {
            dinnerBooking.setNumberOfGuests(dinnerBookingDTO.numberOfGuests());
        }
        if (dinnerBookingDTO.status() != null) {
            dinnerBooking.setStatus(dinnerBookingDTO.status());
        }

        addToRestaurant(dinnerBooking);

        dinnerBookingRepository.save(dinnerBooking);
        return toDTO(dinnerBooking);
    }

    public void addToRestaurant(DinnerBooking dinnerBooking) {
        if (dinnerBooking == null) {
            throw new RuntimeException("No booking found");
        }
        switch (dinnerBooking.getStatus()) {
            case CANCELLED, NO_SHOW:
                break;
            case PAID, BOOKED:
                restaurantService.addBooking(dinnerBooking);
                break;
            default:
                throw new RuntimeException("Invalid status");
        }
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

    public Page<DinnerBookingDTO> getDinnerBookingsByEmail(String customerEmail, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("start"));
        Page<DinnerBooking> bookings = dinnerBookingRepository.findByCustomerEmail(customerEmail, LocalDateTime.now(), pageable);
        return bookings.map(this::toDTO);
    }

    public DinnerBookingDTO addDinnerBooking(DinnerBookingDTO dinnerBookingDTO) {
        var booking = fromDTO(dinnerBookingDTO);
        var savedBooking = dinnerBookingRepository.save(booking);
        addToRestaurant(savedBooking);
        return toDTO(savedBooking);
    }
}
