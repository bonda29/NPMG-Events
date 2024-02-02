package org.example.events.npmg.payload.request;

import lombok.Value;

import java.io.Serializable;

@Value
public class TicketPurchaseRequest implements Serializable {
    Long userId;
    Long ticketTypeId;
}
