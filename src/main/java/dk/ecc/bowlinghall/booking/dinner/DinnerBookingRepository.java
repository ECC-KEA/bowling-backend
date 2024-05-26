package dk.ecc.bowlinghall.booking.dinner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DinnerBookingRepository extends JpaRepository<DinnerBooking, Long> {
    Page<DinnerBooking> findByCustomerEmailAndStartAfter(String customerEmail, LocalDateTime now, Pageable pageable);
}
