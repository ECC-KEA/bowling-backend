package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;

import java.time.LocalDateTime;

public record BowlingBookingResponseDTO(
        Long id,
        Long laneId,
        String customerEmail,
        LocalDateTime start,
        LocalDateTime end,
        Status status
) {
}
