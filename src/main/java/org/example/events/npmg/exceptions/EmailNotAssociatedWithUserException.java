package org.example.events.npmg.exceptions;

public class EmailNotAssociatedWithUserException extends RuntimeException{
	public EmailNotAssociatedWithUserException() {
		super("Email not associated with any user");
	}
}

