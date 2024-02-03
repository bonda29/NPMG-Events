package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.payload.DTOs.EventDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

	private final EventService eventService;

	@PostMapping("/")
	public ResponseEntity<MessageResponse> createEvent(@RequestBody EventDto eventDto) {
		return eventService.createEvent(eventDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
		return eventService.getEventById(id);
	}

	@GetMapping("/")
	public ResponseEntity<List<EventDto>> getAllEvents() {
		return eventService.getAllEvents();
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
		return eventService.updateEvent(id, eventDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteEvent(@PathVariable Long id) {
		return eventService.deleteEvent(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchEvents(@RequestParam String name, @RequestParam String category, @RequestParam String date) {
		LocalDateTime localDateTime = LocalDateTime.parse(date);
		return eventService.searchEvents(name, category, localDateTime);
	}



}