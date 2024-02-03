package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.Role.Role;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/username")
    public ResponseEntity<MessageResponse> changeUserUsername(@PathVariable Long id, @RequestBody String username) {
        return userService.changeUserUsername(id, username);
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<MessageResponse> changeUserBanStatus(@PathVariable Long id) {
        return userService.changeUserBanStatus(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<MessageResponse> changeUserRole(@PathVariable Long id, @RequestBody Set<Role> roles) {
        return userService.changeUserRole(id, roles);
    }
}
