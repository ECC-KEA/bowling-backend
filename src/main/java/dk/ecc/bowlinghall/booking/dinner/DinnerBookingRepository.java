package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DinnerBookingRepository extends JpaRepository<DinnerBooking, Long> {
}
