package org.example.events.npmg.exceptions;

public class EventWithoutDataException extends RuntimeException{
	public EventWithoutDataException(String message) {
		super(message);
	}
}
