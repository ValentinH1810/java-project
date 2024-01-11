package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.RoomEntityDto;
import org.home.hotelmanagementapi.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;


    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomEntityDto>> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomEntityDto> getRoom(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody RoomEntityDto roomEntityDto) {
        return roomService.createRoom(roomEntityDto);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable Long id, @RequestBody RoomEntityDto roomEntityDto) {
        return roomService.updateRoom(id, roomEntityDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id);
    }

}
