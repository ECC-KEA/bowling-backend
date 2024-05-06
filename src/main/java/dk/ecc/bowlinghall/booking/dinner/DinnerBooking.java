package dk.ecc.bowlinghall.booking.dinner;

import dk.ecc.bowlinghall.booking.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class DinnerBooking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created_at;
    private LocalDateTime start;
    private LocalDateTime end;
    private int numberOfGuests;
    private Status status;

}
