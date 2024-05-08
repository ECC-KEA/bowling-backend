// AirHockeyBooking subclass
package dk.ecc.bowlinghall.booking.airhockey;

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
public class AirHockeyBooking extends Booking {
    @ManyToOne
    private AirHockeyTable table;
}