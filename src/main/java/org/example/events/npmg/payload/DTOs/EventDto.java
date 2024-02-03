package org.example.events.npmg.payload.DTOs;

import lombok.Data;
import org.example.events.npmg.models.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link Event}
 */
@Data
public class EventDto {
    private String name;
    private String content;
    private Set<Long> categoriesIds;
    private Long userId;
    private LocalDateTime dateOfCreation;
    private List<String> imageUrls;
}