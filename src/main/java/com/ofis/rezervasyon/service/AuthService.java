package com.ofis.rezervasyon.service;

import org.springframework.stereotype.Service;

import com.ofis.rezervasyon.repository.UserRepository;
import com.ofis.rezervasyon.model.Role;
import com.ofis.rezervasyon.model.User;
import com.ofis.rezervasyon.dto.request.LoginRequest;
import com.ofis.rezervasyon.dto.request.RegisterRequest;
import com.ofis.rezervasyon.dto.response.UserResponse;
import com.ofis.rezervasyon.enums.RoleName;
import com.ofis.rezervasyon.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserResponse register(RegisterRequest request) {
        
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Role userRole = roleRepository.findByName(RoleName.EMPLOYEE)
        .orElseThrow(() -> new RuntimeException("Default role not found"));

        var user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(request.password()) // Temporarily implemented as plaintext, hashing will be added later.
                .role(userRole)
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponse(
            savedUser.getId(),
            savedUser.getFullName(),
            savedUser.getEmail(),
            savedUser.getRole().getName().name()
        );
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!(user.getPassword().equals(request.password()))) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new UserResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole().getName().name()
        );
    }
    
}
