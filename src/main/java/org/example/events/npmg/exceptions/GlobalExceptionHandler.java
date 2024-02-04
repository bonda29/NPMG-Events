package org.example.events.npmg.exceptions;

import org.example.events.npmg.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
        logger.error("ResourceNotFoundException", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(TextToBigException.class)
    public ResponseEntity<Object> handleTextToBigException(TextToBigException exception) {
        logger.error("TextToBigException", exception);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(ObjectWithoutDataException.class)
    public ResponseEntity<Object> handleEventWithoutDataException(ObjectWithoutDataException exception) {
        logger.error("EventWithoutDataException", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(EmailNotAssociatedWithUserException.class)
    public ResponseEntity<Object> handleEmailNotAssociatedWithUserException(EmailNotAssociatedWithUserException exception) {
        logger.error("EmailNotAssociatedWithUserException", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(NotUniqueException.class)
	public ResponseEntity<Object> handleNotUniqueException(NotUniqueException exception) {
		logger.error("NotUniqueException", exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(exception.getMessage()));
	}
}