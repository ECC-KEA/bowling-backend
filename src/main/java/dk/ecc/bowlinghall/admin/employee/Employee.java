package dk.ecc.bowlinghall.admin.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmpType empType;

    private String firstName;

    private String lastName;

    public Employee(EmpType empType, String firstName, String lastName) {
        this.empType = empType;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
