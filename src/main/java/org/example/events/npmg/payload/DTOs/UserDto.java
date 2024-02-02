package org.example.events.npmg.payload.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
	Long id;
	@NotEmpty(message = "Username is mandatory!")
	String username;
	@Email
	String email;
	Date dateOfCreation;
	Boolean valid;
	String imageUrl;
}