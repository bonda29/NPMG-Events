package org.example.events.npmg.payload.DTOs;

import lombok.Data;
import org.example.events.npmg.payload.Location;
import org.example.events.npmg.models.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Event}
 */
@Data
public class EventDto implements Serializable {
	private String name;
	private String description;
	private Location location;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private List<String> imageUrls;
}