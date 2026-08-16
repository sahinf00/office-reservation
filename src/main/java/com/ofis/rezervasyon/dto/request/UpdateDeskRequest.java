package com.ofis.rezervasyon.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateDeskRequest(
    @NotBlank(message = "Desk number is required")
    String deskNumber
) {
}
