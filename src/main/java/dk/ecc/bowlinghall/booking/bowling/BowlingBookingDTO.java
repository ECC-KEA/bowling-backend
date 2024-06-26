package dk.ecc.bowlinghall.booking.bowling;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.ecc.bowlinghall.booking.Status;

import java.time.LocalDateTime;

public record BowlingBookingDTO(
        Long id,
        Long laneId,
        String customerEmail,
        LocalDateTime start,
        LocalDateTime end,
        Status status,
        Boolean childFriendly
) {
}
