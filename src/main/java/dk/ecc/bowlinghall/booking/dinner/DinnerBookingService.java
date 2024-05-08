package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.stereotype.Service;

@Service
public class DinnerBookingService {

    private final DinnerBookingRepository dinnerBookingRepository;

    public DinnerBookingService(DinnerBookingRepository dinnerBookingRepository) {
        this.dinnerBookingRepository = dinnerBookingRepository;
    }
}
