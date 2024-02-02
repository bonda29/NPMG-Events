package org.example.events.npmg.service;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.exceptions.TicketWithoutDataException;
import org.example.events.npmg.models.TicketType;
import org.example.events.npmg.payload.DTOs.TicketTypeDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.TicketTypeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class TicketTypeService {
	private final TicketTypeRepository ticketTypeRepository;
	private final ModelMapper modelMapper;


	public ResponseEntity<MessageResponse> createTicketType(TicketTypeDto data) {
		if (data.getEvent().getId() == null) {
			throw new TicketWithoutDataException("Ticket Type must have an 'event'!");
		}
		else if (data.getName() == null) {
			throw new TicketWithoutDataException("Ticket Type must have a 'name'!");
		}
		else if (data.getPrice() <= 0) {
			throw new TicketWithoutDataException("Ticket Type must have a 'price'!");
		}
		else if (data.getBaseTicketImageUrl() == null) {
			throw new TicketWithoutDataException("Ticket Type must have a base ticket image url!");
		}

		TicketType ticketType = modelMapper.map(data, TicketType.class);
		ticketTypeRepository.save(ticketType);

		return ResponseEntity.ok(new MessageResponse("Ticket Type created successfully!"));
	}

	public ResponseEntity<List<TicketTypeDto>> getAllTicketTypes() {

		List<TicketType> ticketTypes = ticketTypeRepository.findAll();
		Type listType = new TypeToken<List<TicketTypeDto>>() {
		}.getType();
		List<TicketTypeDto> ticketTypeDtos = modelMapper.map(ticketTypes, listType);

		return ResponseEntity.ok().body(ticketTypeDtos);
	}

	public ResponseEntity<TicketTypeDto> getTicketTypeById(Long id) {
		TicketType ticketType = findById(ticketTypeRepository, id);
		TicketTypeDto ticketTypeDto = modelMapper.map(ticketType, TicketTypeDto.class);
		return ResponseEntity.ok().body(ticketTypeDto);
	}

	public ResponseEntity<MessageResponse> updateTicketType(Long id, TicketTypeDto data) {
		TicketType ticketType = findById(ticketTypeRepository, id);
		modelMapper.map(data, ticketType);
		ticketTypeRepository.save(ticketType);
		return ResponseEntity.ok(new MessageResponse("Ticket Type updated successfully!"));
	}

	public ResponseEntity<MessageResponse> deleteTicketType(Long id) {
		ticketTypeRepository.delete(findById(ticketTypeRepository, id));
		return ResponseEntity.ok(new MessageResponse("Ticket Type deleted successfully!"));
	}
}