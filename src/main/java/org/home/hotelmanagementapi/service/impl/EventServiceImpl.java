package org.home.hotelmanagementapi.service.impl;

import jakarta.transaction.Transactional;
import org.home.hotelmanagementapi.model.dto.EventEntityDto;
import org.home.hotelmanagementapi.model.entity.EventEntity;
import org.home.hotelmanagementapi.model.entity.FacilityEntity;
import org.home.hotelmanagementapi.repository.EventRepository;
import org.home.hotelmanagementapi.service.EventService;
import org.home.hotelmanagementapi.service.FacilityService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final FacilityService facilityService;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository,
                            FacilityService facilityService,
                            ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.facilityService = facilityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> createEvent(EventEntityDto eventEntityDto) {

        Optional<EventEntity> optionalEvent = eventRepository.findByName(eventEntityDto.getName());

        if (optionalEvent.isPresent()) {
            return new ResponseEntity<>("Event already exists!", HttpStatus.BAD_REQUEST);
        }

        EventEntity event = modelMapper.map(eventEntityDto, EventEntity.class);
        FacilityEntity facility = facilityService.findFacilityByName(eventEntityDto.getFacilityName());

        if (facility == null) {
            return new ResponseEntity<>("Facility with that name doesn't exists!", HttpStatus.BAD_REQUEST);
        }

        event.setFacility(facility);
        eventRepository.save(event);

        return new ResponseEntity<>("Event created!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<EventEntityDto>> getAllEvents() {

        List<EventEntity> events = eventRepository.findAll();

        if (events.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapEntityListToDtoList(events), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EventEntityDto> getEventByName(String name) {

        Optional<EventEntity> optionalEvent = eventRepository.findByName(name);

        return optionalEvent
                .map(eventEntity -> new ResponseEntity<>(modelMapper.map(eventEntity, EventEntityDto.class), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteByName(String name) {

        Optional<EventEntity> optionalEvent = eventRepository.findByName(name);

        if (optionalEvent.isEmpty()) {
            return new ResponseEntity<>("Event with name: " + name + " doesn't exists!", HttpStatus.BAD_REQUEST);
        }

        eventRepository.delete(optionalEvent.get());

        return new ResponseEntity<>("Event deleted!", HttpStatus.OK);
    }

    private List<EventEntityDto> mapEntityListToDtoList(List<EventEntity> events) {

        List<EventEntityDto> dtoList = new ArrayList<>();

        for (EventEntity event : events) {
            dtoList.add(modelMapper.map(event, EventEntityDto.class));
        }

        return dtoList;
    }
}
