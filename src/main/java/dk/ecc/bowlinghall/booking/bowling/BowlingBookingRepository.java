package dk.ecc.bowlinghall.booking.bowling;

import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BowlingBookingRepository extends JpaRepository<BowlingBooking, Long> {
    List<BowlingBooking> findByCustomerEmail(String customerEmail);
    List<BowlingBooking> findAllByStartBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
