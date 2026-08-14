package com.ofis.rezervasyon.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDeskRequest(
    @NotBlank(message = "Desk number is required")
    String deskNumber
) {
}
