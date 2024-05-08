package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;

public record BowlingBookingDTO(Long id, Long laneId, String customerEmail, String start, String end, Status status){
}
