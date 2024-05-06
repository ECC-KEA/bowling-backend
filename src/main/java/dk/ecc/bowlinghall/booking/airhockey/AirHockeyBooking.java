package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class AirHockeyBooking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created_at;
    @ManyToOne
    private AirHockeyTable table;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
}
