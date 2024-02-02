package org.example.events.npmg.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.example.events.npmg.config.TicketSerializer;
import org.example.events.npmg.config.UserSerializer;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ticket_purchases")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TicketPurchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonSerialize(using = UserSerializer.class)
	private User user;

	@OneToOne
	@JsonSerialize(using = TicketSerializer.class)
	private Ticket ticket;

	@Column(name = "purchase_date")
	private LocalDateTime purchaseDate;
}