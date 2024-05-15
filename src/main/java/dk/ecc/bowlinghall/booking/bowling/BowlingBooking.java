package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Booking;
import dk.ecc.bowlinghall.booking.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BowlingBooking extends Booking {
    @ManyToOne
    private BowlingLane lane;

    public BowlingBooking(String customerEmail, LocalDateTime start, LocalDateTime end, Status status, BowlingLane lane) {
        super(customerEmail, start, end, status);
        setLane(lane);
    }
}
