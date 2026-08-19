package com.ofis.rezervasyon.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.ofis.rezervasyon.enums.ReservationStatus;

@Entity
@Table(name = "reservations", uniqueConstraints = {
    @UniqueConstraint(
        name = "uk_desk_date_status", 
        columnNames = {"desk_id", "reservation_date", "status"}
    )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desk_id", nullable = false)
    private Desk desk;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

}
