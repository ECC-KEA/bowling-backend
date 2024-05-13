package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant getRestaurant(){
        return restaurantRepository.findAll().get(0);
    }
}
