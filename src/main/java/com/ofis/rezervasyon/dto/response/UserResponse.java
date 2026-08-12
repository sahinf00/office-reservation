package com.ofis.rezervasyon.dto.response;


public record UserResponse(
    Long id,
    String fullName,
    String email,
    String roleName
) {
}
