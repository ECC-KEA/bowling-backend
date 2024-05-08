package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Status;

public record AirHockeyBookingDTO(Long id, Long tableId, String customerEmail, String start, String end, Status status) {
}
