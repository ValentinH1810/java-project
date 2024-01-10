package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.EventEntityDto;
import org.home.hotelmanagementapi.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public ResponseEntity<List<EventEntityDto>> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{name}")
    public ResponseEntity<EventEntityDto> getEventByName(@PathVariable String name) {
        return eventService.getEventByName(name);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody EventEntityDto eventEntityDto) {
        return eventService.createEvent(eventEntityDto);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name) {
        return eventService.deleteByName(name);
    }

}
