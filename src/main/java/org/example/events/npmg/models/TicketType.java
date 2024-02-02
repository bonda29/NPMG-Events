package org.example.events.npmg.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.example.events.npmg.config.EventSerializer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "ticket_types")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TicketType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private String description;
	private String baseTicketImageUrl;


	@ManyToOne
	@JoinColumn(name = "event_id")
	@JsonSerialize(using = EventSerializer.class)
	private Event event;

	@JsonIgnore
	@OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
	private List<Ticket> tickets = new ArrayList<>();
}