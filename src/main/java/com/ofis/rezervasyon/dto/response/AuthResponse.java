package com.ofis.rezervasyon.dto.response;

public record AuthResponse(
    String token,
    UserResponse user // added to avoid additional api call or token decoding on the client side to get user info
) {}
