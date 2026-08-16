package com.ofis.rezervasyon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ofis.rezervasyon.dto.request.CreateDeskRequest;
import com.ofis.rezervasyon.dto.request.UpdateDeskRequest;
import com.ofis.rezervasyon.dto.response.DeskResponse;
import com.ofis.rezervasyon.service.DeskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/desks")
@RequiredArgsConstructor
public class DeskController {
    
    private final DeskService deskService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeskResponse> createDesk(@RequestBody @Valid CreateDeskRequest request) {
        DeskResponse response = deskService.createDesk(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeskResponse> updateDesk(@PathVariable Long id, @RequestBody @Valid UpdateDeskRequest request) {
        DeskResponse response = deskService.updateDesk(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDesk(@PathVariable Long id) {
        deskService.deleteDesk(id);
        return ResponseEntity.noContent().build();
    }
}
