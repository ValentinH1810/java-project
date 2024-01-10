package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.EventEntityDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    ResponseEntity<String> createEvent(EventEntityDto eventEntityDto);

    ResponseEntity<List<EventEntityDto>> getAllEvents();

    ResponseEntity<EventEntityDto> getEventByName(String name);

    ResponseEntity<String> deleteByName(String name);
}
