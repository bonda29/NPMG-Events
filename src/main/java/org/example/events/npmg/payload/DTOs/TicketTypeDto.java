package org.example.events.npmg.payload.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.example.events.npmg.config.EventSerializer;
import org.example.events.npmg.models.Event;
import org.example.events.npmg.models.Ticket;
import org.example.events.npmg.models.TicketType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link TicketType}
 */
@Data
public class TicketTypeDto implements Serializable {
	private String name;
	private double price;
	private String description;
	private String baseTicketImageUrl;
	@JsonSerialize(using = EventSerializer.class)
	private Event event;
	private List<Ticket> tickets = new ArrayList<>();
}