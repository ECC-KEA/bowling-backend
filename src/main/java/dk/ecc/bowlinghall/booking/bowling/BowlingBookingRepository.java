package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BowlingBookingRepository extends JpaRepository<BowlingBooking, Long> {
    List<BowlingBooking> findAllByStartBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<BowlingBooking> findByCustomerEmailAndStartAfter(String customerEmail, LocalDateTime now, Pageable pageable);
}
