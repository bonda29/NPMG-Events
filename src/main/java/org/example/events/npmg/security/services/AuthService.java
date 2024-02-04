package org.example.events.npmg.security.services;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.Role.ERole;
import org.example.events.npmg.models.Role.Role;
import org.example.events.npmg.models.User;
import org.example.events.npmg.payload.request.LoginPayload;
import org.example.events.npmg.payload.request.RegisterPayload;
import org.example.events.npmg.payload.response.JwtResponse;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.RoleRepository;
import org.example.events.npmg.repository.UserRepository;
import org.example.events.npmg.security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.events.npmg.util.RepositoryUtil.findByEmail;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private static final MessageResponse USERNAME_TAKEN = new MessageResponse("Username is already taken!");
    private static final MessageResponse EMAIL_IN_USE = new MessageResponse("Email is already in use!");
    private static final MessageResponse PASSWORDS_MISMATCH = new MessageResponse("Passwords do not match!");
    private static final MessageResponse REGISTRATION_SUCCESS = new MessageResponse("User registered successfully!");

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(RegisterPayload data) {
        Optional<MessageResponse> optionalErrorResponse = validateRegisterData(data);
        if (optionalErrorResponse.isPresent()) return ResponseEntity.badRequest().body(optionalErrorResponse.get());

        User user = createUser(data, ERole.ROLE_USER);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> login(LoginPayload data) {
        Authentication authentication = authenticateUser(data);
        String jwt = generateJwtToken(authentication);
        UserDetailsImpl userDetails = getUserDetails(authentication);
        List<String> roles = getRoles(userDetails);
        JwtResponse response = buildJwtResponse(jwt, userDetails, roles);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> changePassword(RegisterPayload data) {
        User user = findByEmail(userRepository, data.getEmail());
        if (encoder.matches(data.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Password is the same as the old one!"));
        }

        if (!arePasswordsMatching(data)) {
            return ResponseEntity.badRequest().body(PASSWORDS_MISMATCH);
        }

        user.setPassword(encoder.encode(data.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
    }

    private Optional<MessageResponse> validateRegisterData(RegisterPayload data) {
        if (userRepository.existsByUsername(data.getUsername())) {
            return Optional.of(USERNAME_TAKEN);
        }
        if (userRepository.existsByEmail(data.getEmail())) {
            return Optional.of(EMAIL_IN_USE);
        }
        if (!arePasswordsMatching(data)) {
            return Optional.of(REGISTRATION_SUCCESS);
        }
        return Optional.empty();
    }

    private User createUser(RegisterPayload data, ERole role) {
        User user = User.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(encoder.encode(data.getPassword()))
                .build();

        user.setRoles(createRolesSet(role));
        return user;
    }

    private Set<Role> createRolesSet(ERole role) {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        return roles;
    }

    private Authentication authenticateUser(LoginPayload data) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
    }

    private String generateJwtToken(Authentication authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    private UserDetailsImpl getUserDetails(Authentication authentication) {
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    private List<String> getRoles(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private JwtResponse buildJwtResponse(String jwt, UserDetailsImpl userDetails, List<String> roles) {
        return JwtResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean arePasswordsMatching(RegisterPayload data) {
        return data.getPassword().equals(data.getRepeatPassword());
    }
}
