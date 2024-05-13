package dk.ecc.bowlinghall.booking.dinner;

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
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;

    @Transient
    @OneToMany
    private List<DinnerBooking> bookings = new ArrayList<>();

    public Restaurant(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Get the remaining capacity of the restaurant at a given timeslot
     * @param start The start of the timeslot
     * @return The remaining capacity
     */
    public int getRemainingCapacityByTimeslot(LocalDateTime start) {
        return capacity - bookings.stream()
                .filter(booking ->
                        booking.getStart().isBefore(start.plusHours(1)) && booking.getEnd().isAfter(start))
                .mapToInt(DinnerBooking::getNumberOfGuests)
                .sum();
    }

    /**
     * Get the remaining capacity of the restaurant between multiple timeslots
     * @param start The start of the timeslot
     * @param end The end of the timeslot
     * @return The remaining capacity
     * @throws IllegalArgumentException if start is after end
     * @throws IllegalArgumentException if start and end are not on the same day
     */
    public int getRemainingCapacityByStartAndEnd(LocalDateTime start, LocalDateTime end) {
        if(start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if(!start.toLocalDate().equals(end.toLocalDate())) {
            throw new IllegalArgumentException("Start and end must be on the same day");
        }
        if(start.equals(end)) {
            return getRemainingCapacityByTimeslot(start);
        }
        var amountOfTimeslots = end.getHour() - start.getHour();
        var capacities = new ArrayList<Integer>();
        for(int i = 0; i < amountOfTimeslots; i++) {
            var remainingCapacity = getRemainingCapacityByTimeslot(start.plusHours(i));
            if(remainingCapacity >= 0){
                capacities.add(remainingCapacity);
            }
        }

        return capacities.stream().min(Integer::compareTo).orElse(0);
    }

    public void addBooking(DinnerBooking booking) {
        bookings.add(booking);
    }

}
