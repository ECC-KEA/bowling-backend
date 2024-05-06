package dk.ecc.bowlinghall.booking.airhockey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AirHockeyTable {
    @Id @GeneratedValue
    private Long id;
    private double price_per_hour;
}
