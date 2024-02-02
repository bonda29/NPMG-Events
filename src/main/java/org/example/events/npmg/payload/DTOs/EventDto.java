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
public class EventDto implements Serializable {
    private Long id;
    private String name;
    private String content;
    private List<String> imageUrls;
    private Long userId;
    private Set<Long> categoriesIds;
    private LocalDateTime dateOfCreation;
}