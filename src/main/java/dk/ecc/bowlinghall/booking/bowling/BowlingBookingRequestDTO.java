package dk.ecc.bowlinghall.booking.bowling;

import java.time.LocalDateTime;

public record BowlingBookingRequestDTO(
        LocalDateTime start,
        LocalDateTime end,
        String customerEmail,
        boolean childFriendly
) {
}
