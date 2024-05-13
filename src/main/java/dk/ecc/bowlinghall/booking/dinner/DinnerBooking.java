// DinnerBooking subclass
package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.booking.Booking;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DinnerBooking extends Booking {
    private int numberOfGuests;

    public DinnerBooking(int numberOfGuests, String customerEmail, LocalDateTime start, LocalDateTime end) {
        super(customerEmail, start, end);
        this.numberOfGuests = numberOfGuests;
    }
}