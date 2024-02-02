package org.example.events.npmg.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.example.events.npmg.payload.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "events")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@Embedded
	private Location location;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "event_image_urls", joinColumns = @JoinColumn(name = "event_id"))
	@Column(name = "image_url")
	private List<String> imageUrls;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private List<TicketType> ticketTypes = new ArrayList<>();

}