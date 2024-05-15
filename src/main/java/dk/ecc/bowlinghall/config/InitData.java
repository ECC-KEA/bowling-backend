package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTable;
import dk.ecc.bowlinghall.booking.airhockey.AirHockeyTableRepository;
import dk.ecc.bowlinghall.booking.bowling.BowlingLane;
import dk.ecc.bowlinghall.booking.bowling.BowlingLaneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

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
        if (bowlingLaneRepository.count() == 0) {
            IntStream.range(0, 24).mapToObj(i -> new BowlingLane(200, i < 4)).forEach(bowlingLaneRepository::save);
        }
        if (airHockeyTableRepository.count() == 0) {
            IntStream.range(0, 6).mapToObj(i -> new AirHockeyTable(50)).forEach(airHockeyTableRepository::save);
        }
    }
}