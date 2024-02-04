package org.example.events.npmg.payload.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.events.npmg.models.Role.Role;
import org.example.events.npmg.models.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Data
public class UserDto {
    @NotEmpty(message = "Username is mandatory!")
    private String username;
    @Email
    private String email;
    private String password; // TODO: remove this field from the DTO
    private Set<Role> roles;
    private LocalDateTime dateOfCreation;
    private String profilePictureUrl;
    private boolean isBanned;
}