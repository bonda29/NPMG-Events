package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.payload.DTOs.TicketTypeDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.service.TicketTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticketTypes")
public class TicketTypeController {

	private final TicketTypeService ticketTypeService;

	@PostMapping
	public ResponseEntity<MessageResponse> createTicketType(@RequestBody TicketTypeDto ticketTypeDto) {
		return ticketTypeService.createTicketType(ticketTypeDto);
	}

	@GetMapping
	public ResponseEntity<List<TicketTypeDto>> getAllTicketTypes() {
		return ticketTypeService.getAllTicketTypes();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TicketTypeDto> getTicketTypeById(@PathVariable Long id) {
		return ticketTypeService.getTicketTypeById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateTicketType(@PathVariable Long id, @RequestBody TicketTypeDto ticketTypeDto) {
		return ticketTypeService.updateTicketType(id, ticketTypeDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteTicketType(@PathVariable Long id) {
		return ticketTypeService.deleteTicketType(id);
	}
}