package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTable;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTableRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingLane;
import dk.ecc.bowlinghall.booking.bowling.BowlingLaneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirHockeyTableRepository airHockeyTableRepository;

    public InitData(BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository) {
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airHockeyTableRepository = airHockeyTableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // create bowling lanes
        List<BowlingLane> bowlingLanes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            boolean childFriendly = i < 4; // first 4 lanes are child-friendly
            bowlingLanes.add(new BowlingLane(200, childFriendly));
        }

        // create air hockey tables
        List<AirHockeyTable> airHockeyTables = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            airHockeyTables.add(new AirHockeyTable(50));
        }

        if (bowlingLaneRepository.count() == 0) {
            bowlingLaneRepository.saveAll(bowlingLanes);
        }
        if (airHockeyTableRepository.count() == 0) {
            airHockeyTableRepository.saveAll(airHockeyTables);
        }
    }
}
