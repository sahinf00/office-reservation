package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.enums.ReservationStatus;
import com.ofis.rezervasyon.model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // prevents N+1 query problem during reservation history retrieval, by forcing eager fetching of associated desk and floor entities
    @Query("SELECT r FROM Reservation r " +
       "JOIN FETCH r.desk d " +
       "JOIN FETCH d.floor " +
       "WHERE r.user.id = :userId " +
       "ORDER BY r.reservationDate DESC")
    List<Reservation> findByUserIdWithDeskAndFloor(@Param("userId") Long userId);

    boolean existsByUserIdAndReservationDateAndStatus(Long userId, LocalDate reservationDate, ReservationStatus status);

    boolean existsByDeskIdAndReservationDateAndStatus(Long deskId, LocalDate reservationDate, ReservationStatus status);
}
