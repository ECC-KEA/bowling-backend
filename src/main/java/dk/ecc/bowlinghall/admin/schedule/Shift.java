package dk.ecc.bowlinghall.admin.schedule;

import dk.ecc.bowlinghall.admin.employee.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private Employee employee;

    private LocalDateTime start;

    private LocalDateTime end;
}
