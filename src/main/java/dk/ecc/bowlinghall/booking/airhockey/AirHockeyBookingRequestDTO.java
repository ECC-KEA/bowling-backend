package dk.ecc.bowlinghall.booking.airhockey;

public record AirHockeyBookingRequestDTO(
        String customerEmail,
        String start,
        String end
) {
}
