package com.ofis.rezervasyon.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
    @NotNull(message = "Desk ID is required")
    Long deskId,

    @NotNull(message = "Reservation date is required")
    @FutureOrPresent(message = "Reservation date cannot be in the past")
    LocalDate reservationDate
) {
    
}
