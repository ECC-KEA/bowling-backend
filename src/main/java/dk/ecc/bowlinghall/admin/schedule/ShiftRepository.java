package dk.ecc.bowlinghall.admin.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findAllByStartBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
