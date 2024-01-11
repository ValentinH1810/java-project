package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.controller.RoomController;
import org.home.hotelmanagementapi.model.dto.RoomEntityDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    void testGetAllRooms() {
        List<RoomEntityDto> roomDtoList = new ArrayList<>();
        when(roomService.getAllRooms()).thenReturn(new ResponseEntity<>(roomDtoList, HttpStatus.OK));

        ResponseEntity<List<RoomEntityDto>> response = roomController.getAllRooms();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roomDtoList, response.getBody());
    }

    @Test
    void testGetRoom() {
        long roomId = 1L;
        RoomEntityDto roomDto = new RoomEntityDto();
        when(roomService.getRoomById(roomId)).thenReturn(new ResponseEntity<>(roomDto, HttpStatus.OK));

        ResponseEntity<RoomEntityDto> response = roomController.getRoom(roomId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roomDto, response.getBody());
    }

    @Test
    void testCreateRoom() {
        RoomEntityDto roomDto = new RoomEntityDto();
        when(roomService.createRoom(roomDto)).thenReturn(new ResponseEntity<>("New room created!", HttpStatus.CREATED));

        ResponseEntity<String> response = roomController.createRoom(roomDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New room created!", response.getBody());
    }

    @Test
    void testUpdateRoom() {
        long roomId = 1L;
        RoomEntityDto roomDto = new RoomEntityDto();
        when(roomService.updateRoom(roomId, roomDto))
                .thenReturn(new ResponseEntity<>("Room with id: 1 was successfully updated", HttpStatus.OK));

        ResponseEntity<String> response = roomController.updateRoom(roomId, roomDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Room with id: 1 was successfully updated", response.getBody());
    }

    @Test
    void testDeleteRoom() {
        long roomId = 1L;
        when(roomService.deleteRoom(roomId)).thenReturn(new ResponseEntity<>("Room with id: 1 was deleted!", HttpStatus.OK));

        ResponseEntity<String> response = roomController.deleteRoom(roomId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Room with id: 1 was deleted!", response.getBody());
    }
}
