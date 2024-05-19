package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AirHockeyTableService {

    private final AirHockeyTableRepository airHockeyTableRepository;

    public AirHockeyTableService(AirHockeyTableRepository airHockeyTableRepository) {
        this.airHockeyTableRepository = airHockeyTableRepository;
    }

    public AirHockeyTableDTO toDTO(AirHockeyTable airHockeyTable) {
        return new AirHockeyTableDTO(airHockeyTable.getId(), airHockeyTable.getPricePerHour());
    }

    public List<AirHockeyTable> getAirHockeyTables() {
        return airHockeyTableRepository.findAll();
    }

    public List<AirHockeyTableDTO> getAirHockeyTableDTOs() {
        return getAirHockeyTables().stream()
                .map(this::toDTO)
                .toList();
    }

    public AirHockeyTable getAirHockeyTableById(Long id) {
        return airHockeyTableRepository.findById(id).orElseThrow();
    }

    public AirHockeyTable saveAirHockeyTable(AirHockeyTable airHockeyTable) {
        return airHockeyTableRepository.save(airHockeyTable);
    }

    public AirHockeyTable findFirstAvailableAirHockeyTable(LocalDateTime start, LocalDateTime end) {
        var tables = getAirHockeyTables();
        return tables.stream()
                .filter(table -> table.isAvailable(start, end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No tables available"));
    }
}
