
package dk.ecc.bowlinghall.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerEmail;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime start;

    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Booking(String customerEmail, LocalDateTime start, LocalDateTime end) {
        this.customerEmail = customerEmail;
        this.start = start;
        this.end = end;
        this.status = Status.BOOKED;
    }

    public Booking(Long id, String customerEmail, LocalDateTime start, LocalDateTime end, Status status) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Booking booking = (Booking) obj;
        return id.equals(booking.id);
    }
}