package org.example.events.npmg.payload;

import lombok.Data;

@Data
public class TicketInfo {
	private String eventName;
	private String ticketType;
	private Double ticketPrice;
	private String userEmail;
	private Long ticketId;

	public String toJson() {
		return "{" +
			"\"eventName\":\"" + eventName + "\"," +
			"\"ticketType\":\"" + ticketType + "\"," +
			"\"ticketPrice\":\"" + ticketPrice + "\"," +
			"\"userEmail\":\"" + userEmail + "\"," +
			"\"ticketId\":\"" + ticketId + "\"" +
			"}";
	}
}

