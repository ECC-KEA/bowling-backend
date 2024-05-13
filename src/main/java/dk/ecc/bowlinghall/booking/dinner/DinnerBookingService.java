package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.error.NotFoundException;
import dk.ecc.bowlinghall.error.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class DinnerBookingService {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final RestaurantService restaurantService;

    public DinnerBookingService(DinnerBookingRepository dinnerBookingRepository, RestaurantService restaurantService) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.restaurantService = restaurantService;
    }

    public DinnerBookingDTO toDTO(DinnerBooking dinnerBooking) {
        return new DinnerBookingDTO(
                dinnerBooking.getId(),
                dinnerBooking.getCustomerEmail(),
                dinnerBooking.getStart(),
                dinnerBooking.getEnd(),
                dinnerBooking.getStatus(),
                dinnerBooking.getNumberOfGuests()
        );
    }

    public DinnerBooking fromDTO(DinnerBookingDTO dinnerBookingDTO) {
        return new DinnerBooking(
                dinnerBookingDTO.id(),
                dinnerBookingDTO.numberOfGuests(),
                dinnerBookingDTO.customerEmail(),
                dinnerBookingDTO.start(),
                dinnerBookingDTO.end(),
                dinnerBookingDTO.status()
        );
    }

    public DinnerBookingDTO get(Long id) {
        return dinnerBookingRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    public DinnerBookingDTO create(DinnerBookingDTO dinnerBookingDTO) {
        DinnerBooking dinnerBooking = fromDTO(dinnerBookingDTO);
        dinnerBookingRepository.save(addToRestaurant(dinnerBooking));
        return toDTO(dinnerBooking);
    }

    public DinnerBookingDTO update(Long id, DinnerBookingDTO dinnerBookingDTO) {
        var dinnerBooking = dinnerBookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        dinnerBooking.setCustomerEmail(dinnerBookingDTO.customerEmail());
        dinnerBooking.setStart(dinnerBookingDTO.start());
        dinnerBooking.setEnd(dinnerBookingDTO.end());
        dinnerBooking.setNumberOfGuests(dinnerBookingDTO.numberOfGuests());
        dinnerBooking.setStatus(dinnerBookingDTO.status());

        dinnerBookingRepository.save(addToRestaurant(dinnerBooking));
        return toDTO(dinnerBooking);
    }

    public DinnerBookingDTO patch(Long id, DinnerBookingDTO dinnerBookingDTO) {
        var dinnerBooking = dinnerBookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        if(dinnerBookingDTO.customerEmail() != null){
            dinnerBooking.setCustomerEmail(dinnerBookingDTO.customerEmail());
        }
        if(dinnerBookingDTO.start() != null){
            dinnerBooking.setStart(dinnerBookingDTO.start());
        }
        if(dinnerBookingDTO.end() != null){
            dinnerBooking.setEnd(dinnerBookingDTO.end());
        }
        if(dinnerBookingDTO.numberOfGuests() != null){
            dinnerBooking.setNumberOfGuests(dinnerBookingDTO.numberOfGuests());
        }
        if(dinnerBookingDTO.status() != null){
            dinnerBooking.setStatus(dinnerBookingDTO.status());
        }

        dinnerBookingRepository.save(addToRestaurant(dinnerBooking));
        return toDTO(dinnerBooking);
    }

    public DinnerBooking addToRestaurant(DinnerBooking dinnerBooking) {
        Restaurant restaurant = restaurantService.getRestaurant();
        if(restaurant == null){
            throw new RuntimeException("No restaurant found");
        }
        var remainingCapacityForTimeslot = restaurant
                .getRemainingCapacityByStartAndEnd(
                        dinnerBooking.getStart(),
                        dinnerBooking.getEnd()
                );
        if(dinnerBooking.getNumberOfGuests() > remainingCapacityForTimeslot){
            throw new ValidationException("Not enough capacity");
        }
        restaurant.addBooking(dinnerBooking);
        return dinnerBooking;
    }
}
