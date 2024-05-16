package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Status;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AirHockeyBookingService {

    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final AirHockeyTableService airHockeyTableService;

    public AirHockeyBookingService(AirHockeyBookingRepository airHockeyBookingRepository, AirHockeyTableService airHockeyTableService) {
        this.airHockeyBookingRepository = airHockeyBookingRepository;
        this.airHockeyTableService = airHockeyTableService;
    }

    public AirHockeyBookingDTO addAirHockeyBooking(AirHockeyBookingDTO airHockeyBookingDTO) {
        var booking = toEntity(airHockeyBookingDTO);
        var savedBooking = airHockeyBookingRepository.save(booking);

        var table = savedBooking.getTable();
        table.addBooking(savedBooking);
        airHockeyTableService.saveAirHockeyTable(table);

        return toDTO(savedBooking);
    }

    public List<AirHockeyBookingDTO> getAirHockeyBookings() {
        List<AirHockeyBooking> bookings = airHockeyBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public Optional<AirHockeyBookingDTO> getAirHockeyBooking(Long id) {
        return airHockeyBookingRepository.findById(id).map(this::toDTO);
    }

    public List<AirHockeyBookingDTO> getAirHockeyBookingsByCustomerEmail(String customerEmail) {
        List<AirHockeyBooking> bookings = airHockeyBookingRepository.findByCustomerEmail(customerEmail);
        return bookings.stream().map(this::toDTO).toList();
    }

    public AirHockeyBookingDTO updatePartialAirHockeyBooking(Long id, AirHockeyBookingDTO dto) {
        Map<String, Object> fields = dtoToMap(dto);
        Optional<AirHockeyBooking> booking = airHockeyBookingRepository.findById(id);
        AtomicBoolean fieldUpdated = new AtomicBoolean(false);
        AtomicBoolean timeChanged = new AtomicBoolean(false);
        AtomicBoolean statusChanged = new AtomicBoolean(false);

        final AirHockeyBooking[] bookingToUpdate = new AirHockeyBooking[1];
        booking.ifPresent(b -> {
            bookingToUpdate[0] = b;
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AirHockeyBooking.class, key);
                if (field != null) {
                    ReflectionUtils.makeAccessible(field);
                    if (key.equals("start") || key.equals("end")) {
                        timeChanged.set(true);
                        if (b.getTable() != null) b.getTable().removeBooking(b);
                    }
                    if (key.equals("status") && (value.equals(Status.CANCELLED) || value.equals(Status.NO_SHOW))) {
                        statusChanged.set(true);
                        if (b.getTable() != null) b.getTable().removeBooking(b);
                    }
                    ReflectionUtils.setField(field, b, value);
                    fieldUpdated.set(true);
                }
            });
        });
        if (timeChanged.get() && !statusChanged.get()) {
            AirHockeyTable table = bookingToUpdate[0].getTable() != null ?
                    airHockeyTableService.findFirstAvailableAirHockeyTable(booking.get().getStart(), booking.get().getEnd()) :
                    null;
            if (table != null) {
                table.addBooking(booking.get());
                airHockeyTableService.saveAirHockeyTable(table);
            } else {
                throw new IllegalArgumentException("No tables available");
            }
        }
        return fieldUpdated.get() ? booking.map(airHockeyBookingRepository::save).map(this::toDTO).orElse(null) : null;
    }

    public Map<String, Object> dtoToMap(AirHockeyBookingDTO dto) {
        Map<String, Object> map = new HashMap<>();
        if (dto.id() != null) map.put("id", dto.id());
        if (dto.tableId() != null) map.put("tableId", dto.tableId());
        if (dto.customerEmail() != null) map.put("customerEmail", dto.customerEmail());
        if (dto.start() != null) map.put("start", dto.start());
        if (dto.end() != null) map.put("end", dto.end());
        if (dto.status() != null) map.put("status", dto.status());
        return map;
    }

    private AirHockeyBookingDTO toDTO(AirHockeyBooking booking) {
        return new AirHockeyBookingDTO(
                booking.getId(),
                booking.getTable().getId(),
                booking.getCustomerEmail(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus());
    }

    private AirHockeyBooking toEntity(AirHockeyBookingDTO bookingDTO) {
        return new AirHockeyBooking(
                bookingDTO.customerEmail(),
                bookingDTO.start(),
                bookingDTO.end(),
                airHockeyTableService.findFirstAvailableAirHockeyTable(bookingDTO.start(), bookingDTO.end())
        );
    }

}
