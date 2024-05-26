package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Booking;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AirHockeyBooking extends Booking {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private AirHockeyTable table;

    public AirHockeyBooking(String customerEmail, LocalDateTime start, LocalDateTime end, AirHockeyTable table) {
        super(customerEmail, start, end);
        this.table = table;
    }

    public double CalculatePrice() {
        return table.getPricePerHour() * Duration.between(this.getStart(), this.getEnd()).toHours();
    }
}