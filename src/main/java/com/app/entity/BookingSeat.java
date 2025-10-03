package com.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_seat",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "session_id", "row_number", "seat_number"
        })
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeat extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;
}
