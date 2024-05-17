package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AirHockeyBookingRepository extends JpaRepository<AirHockeyBooking, Long> {
    List<AirHockeyBooking> findByCustomerEmail(String customerEmail);

    List<AirHockeyBooking> findByStartAfter(LocalDateTime now);
}
