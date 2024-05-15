package dk.ecc.bowlinghall.booking.bowling;

import jakarta.persistence.*;
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
public class BowlingLane {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double pricePerHour;

    private boolean childFriendly;

    @OneToMany
    private List<BowlingBooking> bookings = new ArrayList<>();

    public BowlingLane(double pricePerHour, boolean childFriendly) {
        this.pricePerHour = pricePerHour;
        this.childFriendly = childFriendly;
    }

    public void addBooking(BowlingBooking booking) {
        bookings.add(booking);
    }

    public void removeBooking(BowlingBooking booking) {
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


