package org.example.events.npmg.service;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.exceptions.EventWithoutDataException;
import org.example.events.npmg.models.Event;
import org.example.events.npmg.payload.DTOs.EventDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class EventService {
	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;


	public ResponseEntity<MessageResponse> createEvent(EventDto data) {

		if (data.getName() == null) {
			throw new EventWithoutDataException("Event must have a 'name'!");
		} else if (data.getStartDate() == null || data.getEndDate() == null) {
			throw new EventWithoutDataException("Event must have a proper date!");
		}


		Event event = modelMapper.map(data, Event.class);
		eventRepository.save(event);

		return ResponseEntity.ok(new MessageResponse("Event created successfully!"));
	}

	public ResponseEntity<List<EventDto>> getAllEvents() {

		List<Event> events = eventRepository.findAll();
		Type listType = new TypeToken<List<EventDto>>() {
		}.getType();
		List<EventDto> eventDtos = modelMapper.map(events, listType);

		return ResponseEntity.ok().body(eventDtos);
	}

	public ResponseEntity<EventDto> getEventById(Long id) {
		Event event = findById(eventRepository, id);
		EventDto eventDto = modelMapper.map(event, EventDto.class);
		return ResponseEntity.ok().body(eventDto);
	}

	public ResponseEntity<MessageResponse> updateEvent(Long id, EventDto data) {
		Event event = findById(eventRepository, id);
		modelMapper.map(data, event);
		eventRepository.save(event);
		return ResponseEntity.ok(new MessageResponse("Event updated successfully!"));
	}

	public ResponseEntity<MessageResponse> deleteEvent(Long id) {
		eventRepository.delete(findById(eventRepository, id));
		return ResponseEntity.ok(new MessageResponse("Event deleted successfully!"));
	}
}
