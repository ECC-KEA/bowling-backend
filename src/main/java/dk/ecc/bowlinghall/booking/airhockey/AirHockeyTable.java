package dk.ecc.bowlinghall.booking.airhockey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AirHockeyTable {
    @Id @GeneratedValue
    private Long id;
    private double pricePerHour;

    @OneToMany
    private List<AirHockeyBooking> bookings = new ArrayList<>();

    public AirHockeyTable(double PricePerHour) {
        this.pricePerHour = PricePerHour;
    }

    //TODO clean up nearly identical code in BowlingLane by composing a common interface for BowlingLane and AirHockeyTable
    public void addBooking(AirHockeyBooking booking) {
        bookings.add(booking);
    }

    public void removeBooking(AirHockeyBooking booking) {
        bookings.remove(booking);
    }

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        validateTimes(start, end);
        return bookings.stream().noneMatch(booking -> booking.getStart().isBefore(end) && booking.getEnd().isAfter(start));
    }

    private void validateTimes(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) throw new IllegalArgumentException("Start time must be before end time");
        if (!start.toLocalDate().isEqual(end.toLocalDate()))
            throw new IllegalArgumentException("Start and end time must be on the same date");
        if (start.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Start time must be in the future");
        if (end.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("End time must be in the future");
        if (start.isEqual(end)) throw new IllegalArgumentException("Start and end time cannot be the same");
    }
}
