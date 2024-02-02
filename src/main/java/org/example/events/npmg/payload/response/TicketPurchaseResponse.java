package org.example.events.npmg.payload.response;

import lombok.Data;

@Data
public class TicketPurchaseResponse {
	Ticket ticket;
	TicketPurchase ticketPurchase;
}
