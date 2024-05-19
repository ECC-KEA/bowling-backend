package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.error.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant getRestaurant(){
        return restaurantRepository.findAll().get(0);
    }

    public void save(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

    public void removeBooking(DinnerBooking dinnerBooking){
        var restaurant = getRestaurant();
        restaurant.removeBooking(dinnerBooking);
        save(restaurant);
    }

    public void addBooking(DinnerBooking dinnerBooking){
        var restaurant = getRestaurant();
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
        save(restaurant);
    }

    public boolean isAvailable(LocalDateTime start, LocalDateTime end, int numberOfGuests) {
        System.out.println("RestaurantService.isAvailable");
        var restaurant = getRestaurant();
        System.out.println("restaurant = [" + restaurant + "]");
        System.out.println("start = [" + start + "], end = [" + end + "], numberOfGuests = [" + numberOfGuests + "]");
        int remainingCapacity = restaurant.getRemainingCapacityByStartAndEnd(start, end);
        System.out.println("remainingCapacity = [" + remainingCapacity + "]");
        return remainingCapacity >= numberOfGuests;
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getCapacity()
        );
    }

    public List<RestaurantDTO> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(this::toDTO).toList();

    }
}
