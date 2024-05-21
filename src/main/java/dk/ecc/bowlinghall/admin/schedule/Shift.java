package dk.ecc.bowlinghall.admin.schedule;

import dk.ecc.bowlinghall.admin.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shift {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Employee employee;

    private LocalDateTime start;

    private LocalDateTime end;

    public Shift(Employee employee, LocalDateTime start, LocalDateTime end) {
        this.employee = employee;
        if(start != null && end != null) validateTimes(start, end);
        this.start = start;
        this.end = end;
    }

    public Shift(Long id, Employee employee, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.employee = employee;
        if(start != null && end != null) validateTimes(start, end);
        this.start = start;
        this.end = end;
    }

    public void setStartAndEnd(LocalDateTime start, LocalDateTime end) {
        validateTimes(start, end);
        this.start = start;
        this.end = end;
    }

    private void validateTimes(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (!start.toLocalDate().isEqual(end.toLocalDate())) {
            throw new IllegalArgumentException("Shift must be on the same day");
        }
        if (start.plusHours(1).isAfter(end)){
            throw new IllegalArgumentException("Shift must be at least one hour long");
        }

    }
}
