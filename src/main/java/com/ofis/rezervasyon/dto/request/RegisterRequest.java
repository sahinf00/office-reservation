package com.ofis.rezervasyon.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Full name is required")
    @Size(max = 70, message = "Full name must not exceed 70 characters") 
    String fullName,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format") 
    String email,
    
    @NotBlank(message = "Password is required") 
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password
) {
}
