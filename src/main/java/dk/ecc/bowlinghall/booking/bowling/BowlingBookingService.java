package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.stereotype.Service;

@Service
public class BowlingBookingService {

    private final BowlingBookingRepository bowlingBookingRepository;

    public BowlingBookingService(BowlingBookingRepository bowlingBookingRepository) {
        this.bowlingBookingRepository = bowlingBookingRepository;
    }
}
