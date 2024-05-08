package dk.ecc.bowlinghall.admin.schedule;

import java.time.LocalDateTime;

public record ShiftDTO(Long id, Long employeeId, LocalDateTime start, LocalDateTime end) {
}
