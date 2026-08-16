package com.ofis.rezervasyon.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ofis.rezervasyon.repository.UserRepository;
import com.ofis.rezervasyon.security.CustomUserDetails;
import com.ofis.rezervasyon.security.JwtService;

import jakarta.persistence.EntityNotFoundException;

import com.ofis.rezervasyon.model.Role;
import com.ofis.rezervasyon.model.User;
import com.ofis.rezervasyon.dto.request.LoginRequest;
import com.ofis.rezervasyon.dto.request.RegisterRequest;
import com.ofis.rezervasyon.dto.response.AuthResponse;
import com.ofis.rezervasyon.dto.response.UserResponse;
import com.ofis.rezervasyon.enums.RoleName;
import com.ofis.rezervasyon.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email is already in use");
        }

        Role userRole = roleRepository.findByName(RoleName.EMPLOYEE)
        .orElseThrow(() -> new EntityNotFoundException("Default role not found"));

        var user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(userRole)
                .build();

        User savedUser = userRepository.save(user);
        UserDetails userDetails = new CustomUserDetails(savedUser);
        String token = jwtService.generateToken(userDetails); // Generate token for the newly registered user

        UserResponse userResponse = new UserResponse(
            savedUser.getId(),
            savedUser.getFullName(),
            savedUser.getEmail(),
            savedUser.getRole().getName().name()
        );
        return new AuthResponse(token, userResponse);
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String token = jwtService.generateToken(userDetails);

        UserResponse userResponse = new UserResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole().getName().name()
        );

        return new AuthResponse(token, userResponse);
    }
    
}
