package org.example.events.npmg.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.example.events.npmg.config.TicketPurchaseSerializer;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ticket_type_id")
	private TicketType ticketType;

	@OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
	@JsonSerialize(using = TicketPurchaseSerializer.class)
	private TicketPurchase ticketPurchase;

	private String qrCode;

	private Boolean valid;
}


