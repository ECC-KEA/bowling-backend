package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BowlingBookingRepository extends JpaRepository<BowlingBooking, Long> {
    List<BowlingBooking> findByCustomerEmail(String customerEmail);

    List<BowlingBooking> findByStartAfter(LocalDateTime now);
}
