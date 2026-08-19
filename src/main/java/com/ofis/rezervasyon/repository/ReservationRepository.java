package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.enums.ReservationStatus;
import com.ofis.rezervasyon.model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    boolean existsByUserIdAndReservationDateAndStatus(Long userId, LocalDate reservationDate, ReservationStatus status);

    boolean existsByDeskIdAndReservationDateAndStatus(Long deskId, LocalDate reservationDate, ReservationStatus status);
}
