package dk.ecc.bowlinghall.booking.bowling;


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
public class BowlingBookingService {

    private final BowlingBookingRepository bowlingBookingRepository;
    private final BowlingLaneService bowlingLaneService;

    public BowlingBookingService(BowlingBookingRepository bowlingBookingRepository, BowlingLaneService bowlingLaneService) {
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.bowlingLaneService = bowlingLaneService;
    }

    public BowlingBookingDTO addBowlingBooking(BowlingBookingDTO bowlingBookingDTO) {
        var booking = toEntity(bowlingBookingDTO);
        var savedBooking = bowlingBookingRepository.save(booking);

        var lane = savedBooking.getLane();
        lane.addBooking(savedBooking);
        bowlingLaneService.saveBowlingLane(lane);

        return toDTO(savedBooking);
    }

    public List<BowlingBookingDTO> getBowlingBookings() {
        List<BowlingBooking> bookings = bowlingBookingRepository.findAll();
        return bookings.stream().map(this::toDTO).toList();
    }

    public Optional<BowlingBookingDTO> getBowlingBooking(Long id) {
        return bowlingBookingRepository.findById(id).map(this::toDTO);
    }

    public List<BowlingBookingDTO> getBowlingBookingsByCustomerEmail(String customerEmail) {
        List<BowlingBooking> bookings = bowlingBookingRepository.findByCustomerEmail(customerEmail);
        return bookings.stream().map(this::toDTO).toList();
    }

    /**
     * This method is used to partially update a BowlingBooking entity.
     * It takes an id and a map of fields to update as parameters.
     * It first retrieves the BowlingBooking entity from the repository by id.
     * It then iterates over the fields map and for each field, it uses reflection to find the field in the BowlingBooking entity.
     * If the field to update is 'start', 'end' or 'status' (with a value of CANCELLED or NO_SHOW), it removes the booking from the lane.
     * if the field to update is 'start' or 'end', it finds the first available BowlingLane for the new time period and adds the booking to it.
     * Finally, it saves the updated BowlingBooking entity to the repository and returns the updated BowlingBookingDTO.
     *
     * @param id     The id of the BowlingBooking entity to update
     * @param dto  The BowlingBookingDTO entity containing the fields to update
     * @return The updated BowlingBookingDTO entity or null if no fields were updated
     * @throws IllegalArgumentException if no lanes are available
     */
    public BowlingBookingDTO updatePartialBowlingBooking(Long id, BowlingBookingDTO dto) {
        Map<String, Object> fields = dtoToMap(dto);
        Optional<BowlingBooking> booking = bowlingBookingRepository.findById(id);
        AtomicBoolean fieldUpdated = new AtomicBoolean(false);
        AtomicBoolean timeChanged = new AtomicBoolean(false);
        AtomicBoolean statusChanged = new AtomicBoolean(false);

        final BowlingBooking[] bookingToUpdate = new BowlingBooking[1];
        booking.ifPresent(b -> {
            bookingToUpdate[0] = b;
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(BowlingBooking.class, key);
                if (field != null) {
                    ReflectionUtils.makeAccessible(field);
                    if (key.equals("start") || key.equals("end")) {
                        timeChanged.set(true);
                        if (b.getLane() != null) b.getLane().removeBooking(b);
                    }
                    if (key.equals("status") && (value.equals(Status.CANCELLED) || value.equals(Status.NO_SHOW))) {
                        statusChanged.set(true);
                        if (b.getLane() != null) b.getLane().removeBooking(b);
                    }
                    if (key.equals("laneId") && !statusChanged.get() && !timeChanged.get()) {
                        BowlingLane lane = bowlingLaneService.getBowlingLaneById((Long) value);
                        ReflectionUtils.setField(field, b, lane);
                    }
                    ReflectionUtils.setField(field, b, value);
                    fieldUpdated.set(true);
                }
            });
        });
        if (timeChanged.get() && !statusChanged.get()) {
            BowlingLane lane = bookingToUpdate[0].getLane() != null ?
                    bowlingLaneService.findFirstAvailableBowlingLane(booking.get().getStart(), booking.get().getEnd(), bookingToUpdate[0].getLane().isChildFriendly()) :
                    null;
            if (lane != null) {
                lane.addBooking(booking.get());
                bowlingLaneService.saveBowlingLane(lane);
            } else {
                throw new IllegalArgumentException("No lanes available");
            }
        }
        return fieldUpdated.get() ? booking.map(bowlingBookingRepository::save).map(this::toDTO).orElse(null) : null;
    }

    private Map<String, Object> dtoToMap(BowlingBookingDTO dto) {
        Map<String, Object> map = new HashMap<>();
        if (dto.id() != null) map.put("id", dto.id());
        if (dto.laneId() != null) map.put("lane", dto.laneId());
        if (dto.customerEmail() != null) map.put("customerEmail", dto.customerEmail());
        if (dto.start() != null) map.put("start", dto.start());
        if (dto.end() != null) map.put("end", dto.end());
        if (dto.status() != null) map.put("status", dto.status());
        if (dto.childFriendly() != null) map.put("childFriendly", dto.childFriendly());
        return map;

    }

    private BowlingBookingDTO toDTO(BowlingBooking booking) {
        return new BowlingBookingDTO(
                booking.getId(),
                booking.getLane().getId(),
                booking.getCustomerEmail(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus(),
                booking.getLane().isChildFriendly()
        );
    }

    private BowlingBooking toEntity(BowlingBookingDTO requestDTO) {
        return new BowlingBooking(
                requestDTO.customerEmail(),
                requestDTO.start(),
                requestDTO.end(),
                bowlingLaneService.findFirstAvailableBowlingLane(requestDTO.start(), requestDTO.end(), requestDTO.childFriendly())
        );
    }
}
