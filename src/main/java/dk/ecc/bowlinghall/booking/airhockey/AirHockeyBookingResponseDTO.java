package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Status;

public record AirHockeyBookingResponseDTO(
        Long id,
        Long tableId,
        String customerEmail,
        String start,
        String end,
        Status status
) {
}
