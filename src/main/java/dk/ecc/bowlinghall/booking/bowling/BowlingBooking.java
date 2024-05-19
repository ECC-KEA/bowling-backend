package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Booking;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @ManyToOne(cascade = CascadeType.ALL)
    private BowlingLane lane;

    public BowlingBooking(String customerEmail, LocalDateTime start, LocalDateTime end, BowlingLane lane) {
        super(customerEmail, start, end);
        this.lane = lane;
    }
}
