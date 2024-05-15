package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DinnerBookingRepository extends JpaRepository<DinnerBooking, Long> {
    List<DinnerBooking> findByCustomerEmail(String customerEmail);
}
