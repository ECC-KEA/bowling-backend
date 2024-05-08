package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.stereotype.Service;

@Service
public class AirHockeyBookingService {

    private final AirHockeyBookingRepository airHockeyBookingRepository;

    public AirHockeyBookingService(AirHockeyBookingRepository airHockeyBookingRepository) {
        this.airHockeyBookingRepository = airHockeyBookingRepository;
    }
}
