package org.example.events.npmg.payload.response;

import lombok.Data;
import org.example.events.npmg.models.Ticket;
import org.example.events.npmg.models.TicketPurchase;

@Data
public class TicketPurchaseResponse {
	Ticket ticket;
	TicketPurchase ticketPurchase;
}
