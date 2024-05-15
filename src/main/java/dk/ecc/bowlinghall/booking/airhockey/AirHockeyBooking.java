package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Booking;
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
public class AirHockeyBooking extends Booking {
    @ManyToOne
    private AirHockeyTable table;

    public AirHockeyBooking(String customerEmail, LocalDateTime start, LocalDateTime end, AirHockeyTable table) {
        super(customerEmail, start, end);
        this.table = table;
    }
}