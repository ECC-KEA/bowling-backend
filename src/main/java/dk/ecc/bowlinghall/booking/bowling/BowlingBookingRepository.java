package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowlingBookingRepository extends JpaRepository<BowlingBooking, Long> {
    List<BowlingBooking> findByCustomerEmail(String customerEmail);
}
