package com.ofis.rezervasyon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ofis.rezervasyon.dto.request.CreateFloorRequest;
import com.ofis.rezervasyon.dto.response.FloorResponse;
import com.ofis.rezervasyon.service.DeskService;
import com.ofis.rezervasyon.service.FloorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
public class FloorController {
    private final FloorService floorService;
    private final DeskService deskService;

    @PostMapping
    public ResponseEntity<FloorResponse> createFloor(@RequestBody @Valid CreateFloorRequest request) {
        FloorResponse response = floorService.createFloor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FloorResponse>> getAllFloors() {
        List<FloorResponse> response = floorService.getAllFloors();
        return ResponseEntity.ok(response);
    }
    
}
