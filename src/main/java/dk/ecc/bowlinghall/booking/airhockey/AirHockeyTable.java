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
    private double pricePerHour;

    public AirHockeyTable(double pricePerHour) {
        setPricePerHour(pricePerHour);
    }
}
