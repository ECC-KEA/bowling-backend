package dk.ecc.bowlinghall.booking.bowling;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlingBookingRepository extends JpaRepository<BowlingBooking, Long> {
}
