package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.RoomEntityDto;
import org.home.hotelmanagementapi.model.entity.RoomEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {
    ResponseEntity<String> createRoom(RoomEntityDto roomEntityDto);

    ResponseEntity<List<RoomEntityDto>> getAllRooms();

    ResponseEntity<RoomEntityDto> getRoomById(Long id);

    ResponseEntity<String> updateRoom(Long id, RoomEntityDto roomEntityDto);

    ResponseEntity<String> deleteRoom(Long id);

    RoomEntity findRoom(Long id);
}