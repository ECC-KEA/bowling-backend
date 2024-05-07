package dk.ecc.bowlinghall.booking.bowling;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BowlingLane {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price_per_hour;
    private boolean childFriendly;
}