package dk.ecc.bowlinghall.booking.airhockey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AirHockeyBookingRepository extends JpaRepository<AirHockeyBooking, Long> {
    List<AirHockeyBooking> findAllByStartBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<AirHockeyBooking> findByCustomerEmailAndStartAfter(String customerEmail, LocalDateTime now, Pageable pageable);
}
