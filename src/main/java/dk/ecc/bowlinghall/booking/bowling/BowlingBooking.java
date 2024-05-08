package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Booking;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BowlingBooking extends Booking {
    @ManyToOne
    private BowlingLane lane;
}
