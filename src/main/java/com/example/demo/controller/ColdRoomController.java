package com.example.demo.controller;

import com.example.demo.dto.ColdRoomRequest;
import com.example.demo.dto.ColdRoomResponse;
import com.example.demo.entity.ColdRoom;
import com.example.demo.service.ColdRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cold-rooms")
@Tag(name = "Cold Rooms", description = "Cold room management endpoints")
public class ColdRoomController {
    private final ColdRoomService coldRoomService;
    
    public ColdRoomController(ColdRoomService coldRoomService) {
        this.coldRoomService = coldRoomService;
    }
    
    @PostMapping
    @Operation(summary = "Create new cold room")
    public ResponseEntity<ColdRoomResponse> createColdRoom(@RequestBody ColdRoomRequest request) {
        ColdRoom coldRoom = new ColdRoom(request.getName(), request.getLocation(), 
                                        request.getMinAllowed(), request.getMaxAllowed());
        coldRoom = coldRoomService.createColdRoom(coldRoom);
        
        ColdRoomResponse response = mapToResponse(coldRoom);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all cold rooms")
    public ResponseEntity<List<ColdRoomResponse>> getAllColdRooms() {
        List<ColdRoom> coldRooms = coldRoomService.getAllColdRooms();
        List<ColdRoomResponse> responses = coldRooms.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    private ColdRoomResponse mapToResponse(ColdRoom coldRoom) {
        ColdRoomResponse response = new ColdRoomResponse();
        response.setId(coldRoom.getId());
        response.setName(coldRoom.getName());
        response.setLocation(coldRoom.getLocation());
        response.setMinAllowed(coldRoom.getMinAllowed());
        response.setMaxAllowed(coldRoom.getMaxAllowed());
        return response;
    }
}