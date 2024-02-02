package org.example.events.npmg.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.events.npmg.payload.request.LoginPayload;
import org.example.events.npmg.payload.request.RegisterPayload;
import org.example.events.npmg.security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterPayload data) {
		return authService.registerUser(data);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginPayload data) {
		return authService.login(data);
	}
}