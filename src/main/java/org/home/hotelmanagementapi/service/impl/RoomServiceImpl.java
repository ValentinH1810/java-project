package org.home.hotelmanagementapi.service.impl;

import jakarta.transaction.Transactional;
import org.home.hotelmanagementapi.model.dto.RoomEntityDto;
import org.home.hotelmanagementapi.model.entity.RoomEntity;
import org.home.hotelmanagementapi.repository.RoomRepository;
import org.home.hotelmanagementapi.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public ResponseEntity<String> createRoom(RoomEntityDto roomEntityDto) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomEntityDto.getId());

        if (optionalRoom.isEmpty()) {
            roomRepository.save(mapRoomFromDtoToEntity(roomEntityDto));
            return new ResponseEntity<>("New room created!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Room with id: " + roomEntityDto.getId() + " already exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<RoomEntityDto>> getAllRooms() {

        return new ResponseEntity<>(mapAllEntitiesToDto(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RoomEntityDto> getRoomById(Long id) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(id);

        return optionalRoom
                .map(roomEntity -> new ResponseEntity<>(mapRoomFromEntityToDto(roomEntity), HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @Override
    @Transactional
    public ResponseEntity<String> updateRoom(Long id, RoomEntityDto roomEntityDto) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isEmpty()){
            return new ResponseEntity<>("Room with id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND);
        }

        updateRoomEntity(optionalRoom.get() , roomEntityDto);
        return new ResponseEntity<>("Room with id: " + id + " was successfully updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteRoom(Long id) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(id);

        optionalRoom.ifPresent(roomRepository::delete);

        return optionalRoom.isEmpty()
                ? new ResponseEntity<>("Room with id: " + id + " doesn't exist!", HttpStatus.NOT_FOUND)
                :  new ResponseEntity<>("Room with id: " + id + " was deleted!", HttpStatus.OK);
    }

    @Override
    public RoomEntity findRoom(Long id) {

        Optional<RoomEntity> optionalRoom = roomRepository.findById(id);

        return optionalRoom.orElse(null);
    }


    private void updateRoomEntity(RoomEntity room, RoomEntityDto updateDto) {

        room.setType(updateDto.getType());
        room.setStatus(updateDto.getStatus());
        room.setPricePerDay(updateDto.getPricePerDay());

        roomRepository.save(room);
    }

    private List<RoomEntityDto> mapAllEntitiesToDto() {

        List<RoomEntity> entities = roomRepository.findAll();
        List<RoomEntityDto> dtos = new ArrayList<>();

        for (RoomEntity entity : entities) {
            dtos.add(mapRoomFromEntityToDto(entity));
        }

        return dtos;
    }

    private RoomEntityDto mapRoomFromEntityToDto(RoomEntity entity) {
        RoomEntityDto dto = new RoomEntityDto();

        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setPricePerDay(entity.getPricePerDay());

        return dto;
    }

    private RoomEntity mapRoomFromDtoToEntity(RoomEntityDto dto) {
        RoomEntity entity = new RoomEntity();

        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setPricePerDay(dto.getPricePerDay());

        return entity;
    }
}