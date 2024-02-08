package org.example.events.npmg.service;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.config.Mapper.EventMapper;
import org.example.events.npmg.exceptions.ObjectWithoutDataException;
import org.example.events.npmg.models.Event;
import org.example.events.npmg.payload.DTOs.EventDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventMapper eventMapper;


    public ResponseEntity<MessageResponse> createEvent(EventDto data) {
        if (data.getName() == null) {
            throw new ObjectWithoutDataException("Event must have a 'name'!");
        } else if (data.getContent() == null) {
            throw new ObjectWithoutDataException("Event must have a 'content'!");
        } else if (data.getCategoryId() == null) {
            throw new ObjectWithoutDataException("Event must have a 'categoryId'!");
        } else if (data.getUserId() == null) {
            throw new ObjectWithoutDataException("Event must have a 'userId'!");
        } else if (data.getName().length() > 100) {
            throw new ObjectWithoutDataException("Event name is too long!");
        }


        Event event = eventMapper.toEntity(data);
        eventRepository.save(event);

        return ResponseEntity.ok(new MessageResponse("Event created successfully!"));
    }

    public ResponseEntity<EventDto> getEventById(Long id) {
        Event event = findById(eventRepository, id);
        EventDto eventDto = eventMapper.toDto(event);
        return ResponseEntity.ok().body(eventDto);
    }

    public ResponseEntity<List<EventDto>> getAllEvents() {

        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = eventMapper.toDto(events);

        return ResponseEntity.ok().body(eventDtos);
    }

    public ResponseEntity<List<EventDto>> getLatest() {
        List<Event> events = eventRepository.findLatest().orElse(null);
        List<EventDto> eventDtos = eventMapper.toDto(events);
        return ResponseEntity.ok().body(eventDtos);
    }

    public ResponseEntity<MessageResponse> updateEvent(Long id, EventDto data) {
        //TODO: validate data
        Event event = findById(eventRepository, id);
        modelMapper.map(data, event);
        eventRepository.save(event);
        return ResponseEntity.ok(new MessageResponse("Event updated successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteEvent(Long id) {
        eventRepository.delete(findById(eventRepository, id));
        return ResponseEntity.ok(new MessageResponse("Event deleted successfully!"));
    }

    public ResponseEntity<?> searchEvents(String eventName, String categoryName, LocalDateTime date) {
        Optional<List<Event>> events = eventRepository.searchEventsByNameCategoryDate(eventName, categoryName, date);
        if (events.isPresent()) {
            return ResponseEntity.ok().body(eventMapper.toDto(events.get()));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("No events found!"));
    }
}
