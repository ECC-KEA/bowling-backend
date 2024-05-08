package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.stereotype.Service;

@Service
public class AirHockeyTableService {

    private final AirHockeyTableRepository airHockeyTableRepository;

    public AirHockeyTableService(AirHockeyTableRepository airHockeyTableRepository) {
        this.airHockeyTableRepository = airHockeyTableRepository;
    }
}
