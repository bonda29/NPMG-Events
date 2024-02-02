package org.example.events.npmg.config.Mapper;


import org.example.events.npmg.models.Category;
import org.example.events.npmg.models.Event;
import org.example.events.npmg.payload.DTOs.EventDto;
import org.example.events.npmg.repository.CategoryRepository;
import org.example.events.npmg.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Component
public class EventMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public EventMapper() {
        modelMapper.addMappings(new PropertyMap<Event, EventDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
                map().setCategoriesIds(source.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
            }
        });
    }

    public EventDto toDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    public List<EventDto> toDto(List<Event> events) {
        Type listType = new TypeToken<List<EventDto>>() {}.getType();
        return modelMapper.map(events, listType);
    }

    public Event toEntity(EventDto dto) {
        Event event = modelMapper.map(dto, Event.class);
        event.setUser(findById(userRepository, dto.getUserId()));
        event.setCategories(dto.getCategoriesIds().stream().map(id -> findById(categoryRepository, id)).collect(Collectors.toSet()));
        return event;
    }
}
