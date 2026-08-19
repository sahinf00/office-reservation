package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.model.Desk;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeskRepository extends JpaRepository<Desk, Long> {

    // pessimistic lock implementation to prevent concurrent updates on the same desk record
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM Desk d WHERE d.id = :id")
    Optional<Desk> findByIdWithLock(@Param("id") Long id);

    List<Desk> findByIsActiveTrue();
    List<Desk> findByFloorId(Long floorId);
    List<Desk> findByFloorIdAndIsActiveTrue(Long floorId);
    boolean existsByDeskNumberAndFloorId(String deskNumber, Long floorId);
    boolean existsByDeskNumberAndFloorIdAndIsActiveTrue(String deskNumber, Long floorId);
}
