package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.EventEntityDto;
import org.home.hotelmanagementapi.model.entity.EventEntity;
import org.home.hotelmanagementapi.model.entity.FacilityEntity;
import org.home.hotelmanagementapi.repository.EventRepository;
import org.home.hotelmanagementapi.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private FacilityService facilityService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testCreateEventSuccess() {
        EventEntityDto eventDto = new EventEntityDto();
        eventDto.setName("NewEvent");
        eventDto.setDateAndTime(LocalDateTime.now());
        eventDto.setFacilityName("Conference Room");

        when(eventRepository.findByName("NewEvent")).thenReturn(Optional.empty());
        when(facilityService.findFacilityByName("Conference Room")).thenReturn(new FacilityEntity());
        when(modelMapper.map(eventDto, EventEntity.class)).thenReturn(new EventEntity());

        ResponseEntity<String> response = eventService.createEvent(eventDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Event created!", response.getBody());

        verify(eventRepository, times(1)).findByName("NewEvent");
        verify(facilityService, times(1)).findFacilityByName("Conference Room");
        verify(eventRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(eventDto, EventEntity.class);
    }

    @Test
    void testCreateEventFailureEventExists() {
        EventEntityDto eventDto = new EventEntityDto();
        eventDto.setName("ExistingEvent");
        eventDto.setDateAndTime(LocalDateTime.now());
        eventDto.setFacilityName("Conference Room");

        when(eventRepository.findByName("ExistingEvent")).thenReturn(Optional.of(new EventEntity()));

        ResponseEntity<String> response = eventService.createEvent(eventDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Event already exists!", response.getBody());

        verify(eventRepository, times(1)).findByName("ExistingEvent");
        verify(facilityService, never()).findFacilityByName(any());
        verify(eventRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), eq(EventEntity.class));
    }

    @Test
    void testGetAllEventsSuccess() {
        List<EventEntity> events = new ArrayList<>();
        events.add(new EventEntity("Event1", LocalDateTime.now(), new FacilityEntity()));
        events.add(new EventEntity("Event2", LocalDateTime.now().plusDays(1), new FacilityEntity()));

        when(eventRepository.findAll()).thenReturn(events);
        when(modelMapper.map(any(), eq(EventEntityDto.class))).thenAnswer(invocation -> {
            EventEntity source = invocation.getArgument(0);
            EventEntityDto target = new EventEntityDto();
            target.setName(source.getName());
            target.setDateAndTime(source.getDateAndTime());
            target.setFacilityName(source.getFacility().getName());
            return target;
        });

        ResponseEntity<List<EventEntityDto>> response = eventService.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(eventRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(), eq(EventEntityDto.class));
    }

    @Test
    void testGetAllEventsEmptyList() {
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<EventEntityDto>> response = eventService.getAllEvents();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(eventRepository, times(1)).findAll();
        verify(modelMapper, never()).map(any(), eq(EventEntityDto.class));
    }

    @Test
    void testGetEventByNameSuccess() {
        String eventName = "Event1";
        EventEntity eventEntity = new EventEntity(eventName, LocalDateTime.now(), new FacilityEntity());

        when(eventRepository.findByName(eventName)).thenReturn(Optional.of(eventEntity));
        when(modelMapper.map(eventEntity, EventEntityDto.class)).thenReturn(new EventEntityDto());

        ResponseEntity<EventEntityDto> response = eventService.getEventByName(eventName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(eventRepository, times(1)).findByName(eventName);
        verify(modelMapper, times(1)).map(eventEntity, EventEntityDto.class);
    }

    @Test
    void testGetEventByNameNotFound() {
        String eventName = "NonExistingEvent";

        when(eventRepository.findByName(eventName)).thenReturn(Optional.empty());

        ResponseEntity<EventEntityDto> response = eventService.getEventByName(eventName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(eventRepository, times(1)).findByName(eventName);
        verify(modelMapper, never()).map(any(), eq(EventEntityDto.class));
    }

    @Test
    void testDeleteByNameSuccess() {
        String eventName = "EventToDelete";
        EventEntity eventEntity = new EventEntity(eventName, LocalDateTime.now(), new FacilityEntity());

        when(eventRepository.findByName(eventName)).thenReturn(Optional.of(eventEntity));

        ResponseEntity<String> response = eventService.deleteByName(eventName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event deleted!", response.getBody());

        verify(eventRepository, times(1)).findByName(eventName);
        verify(eventRepository, times(1)).delete(eventEntity);
    }

    @Test
    void testDeleteByNameNotFound() {
        String eventName = "NonExistingEvent";

        when(eventRepository.findByName(eventName)).thenReturn(Optional.empty());

        ResponseEntity<String> response = eventService.deleteByName(eventName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Event with name: NonExistingEvent doesn't exists!", response.getBody());

        verify(eventRepository, times(1)).findByName(eventName);
        verify(eventRepository, never()).delete(any());
    }
}