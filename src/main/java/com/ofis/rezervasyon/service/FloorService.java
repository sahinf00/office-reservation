package com.ofis.rezervasyon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ofis.rezervasyon.dto.request.CreateFloorRequest;
import com.ofis.rezervasyon.dto.response.DeskResponse;
import com.ofis.rezervasyon.dto.response.FloorResponse;
import com.ofis.rezervasyon.model.Desk;
import com.ofis.rezervasyon.model.Floor;
import com.ofis.rezervasyon.repository.FloorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final FloorRepository floorRepository;

    public FloorResponse createFloor(CreateFloorRequest request) {
        if(floorRepository.existsByFloorNumber(request.floorNumber())) {
            throw new RuntimeException("Floor with number " + request.floorNumber() + " already exists.");
        }

        Floor floor = new Floor();
        floor.setFloorNumber(request.floorNumber());
        floor.setName(request.name());
        Floor savedFloor = floorRepository.save(floor);
        return new FloorResponse(
            savedFloor.getId(),
            savedFloor.getFloorNumber(),
            savedFloor.getName(),
            List.of() // Initialize with an empty list of desks
        );
    }

    public List<FloorResponse> getAllFloors() {
        List<Floor> floors = floorRepository.findAll();
        return floors.stream()
                .map(floor -> {
                    // filters the desks to only include active ones and maps them to DeskResponse
                    List<DeskResponse> activeDesks = (floor.getDesks() == null) ? List.of() :
                        floor.getDesks().stream()
                            .filter(Desk::isActive)
                            .map(desk -> new DeskResponse(
                                desk.getId(),
                                desk.getDeskNumber(),
                                desk.isActive(),
                                floor.getId()
                            ))
                            .toList();

                    // FloorResponse is given activeDesks as the list of desks
                    return new FloorResponse(
                        floor.getId(),
                        floor.getFloorNumber(),
                        floor.getName(),
                        activeDesks
                    );
                })
                .toList();
    }
}
