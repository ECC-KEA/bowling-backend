package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BowlingBooking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerEmail;

    @CreationTimestamp
    private LocalDateTime created_at;

    @ManyToOne
    private BowlingLane lane;

    private LocalDateTime start;

    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    private Status status;


}
