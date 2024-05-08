// DinnerBooking subclass
package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.booking.Booking;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DinnerBooking extends Booking {
    private int numberOfGuests;
}