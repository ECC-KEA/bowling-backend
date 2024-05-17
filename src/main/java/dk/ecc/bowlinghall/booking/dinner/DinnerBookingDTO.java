package dk.ecc.bowlinghall.booking.dinner;

import com.fasterxml.jackson.annotation.JsonFormat;
import dk.ecc.bowlinghall.booking.Status;

import java.time.LocalDateTime;

public record DinnerBookingDTO(
        Long id,
        String customerEmail,
        LocalDateTime start,
        LocalDateTime end,
        Status status,
        Integer numberOfGuests
) {
}
