package org.example.events.npmg.exceptions;

public class TicketWithoutDataException extends RuntimeException {
	public TicketWithoutDataException(String message) {
		super(message);
	}
}