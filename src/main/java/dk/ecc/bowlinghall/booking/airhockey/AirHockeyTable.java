package dk.ecc.bowlinghall.booking.airhockey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AirHockeyTable {
    @Id @GeneratedValue
    private Long id;
    private double price_per_hour;

    public AirHockeyTable(double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }
}
