package com.ofis.rezervasyon.dto.response;

import java.time.LocalDate;

import com.ofis.rezervasyon.enums.ReservationStatus;

public record ReservationResponse(
    Long id,
    Long deskId,
    String deskNumber,
    Integer floorNumber,
    Long userId,
    String userFullName,
    LocalDate reservationDate,
    ReservationStatus status
) {

}
