package org.example.events.npmg.payload.request;

import jakarta.validation.constraints.Email;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Value
public class RegisterPayload implements Serializable {
	String username;
	@Email
	String email;
	Set<String> role;
	String password;
	String repeatPassword;
	List<String> imageUrls;
}
