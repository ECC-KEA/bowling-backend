package dk.ecc.bowlinghall.booking.airhockey;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.ecc.bowlinghall.booking.Status;

import java.time.LocalDateTime;

public record AirHockeyBookingDTO(
        Long id,
        Long tableId,
        String customerEmail,
        LocalDateTime start,
        LocalDateTime end,
        Status status
) {
}
