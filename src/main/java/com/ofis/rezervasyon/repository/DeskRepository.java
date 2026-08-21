package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.dto.response.FloorOccupancyResponse;
import com.ofis.rezervasyon.model.Desk;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DeskRepository extends JpaRepository<Desk, Long> {

    // pessimistic lock implementation to prevent concurrent updates on the same desk record
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM Desk d WHERE d.id = :id")
    Optional<Desk> findByIdWithLock(@Param("id") Long id);

    @Query("SELECT new com.ofis.rezervasyon.dto.response.FloorOccupancyResponse(" +
       "d.floor.id, " +
       "d.floor.number, " +
       "d.floor.name, " +
       "COUNT(DISTINCT d.id), " +
       "COUNT(DISTINCT r.id), " +
       "0.0) " +
       "FROM Desk d " +
       "LEFT JOIN Reservation r ON r.desk = d " +
       "AND r.reservationDate = :today " +
       "AND r.status = com.ofis.rezervasyon.model.enums.ReservationStatus.CONFIRMED " +
       "WHERE d.isActive = true " +
       "GROUP BY d.floor.id, d.floor.number, d.floor.name")
    List<FloorOccupancyResponse> findOccupancyByFloorRaw(@Param("today") LocalDate today);

    List<Desk> findByIsActiveTrue();
    List<Desk> findByFloorId(Long floorId);
    List<Desk> findByFloorIdAndIsActiveTrue(Long floorId);
    boolean existsByDeskNumberAndFloorId(String deskNumber, Long floorId);
    boolean existsByDeskNumberAndFloorIdAndIsActiveTrue(String deskNumber, Long floorId);
}
