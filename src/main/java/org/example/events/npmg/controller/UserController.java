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

    @PutMapping("/{userId}/username")
    public ResponseEntity<MessageResponse> changeUserUsername(@PathVariable Long userId, @RequestBody String username) {
        return userService.changeUserUsername(userId, username);
    }

    @PutMapping("/{userId}/ban")
    public ResponseEntity<MessageResponse> changeUserBanStatus(@PathVariable Long userId) {
        return userService.changeUserBanStatus(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<MessageResponse> changeUserRole(@PathVariable Long userId, @RequestBody Set<Role> roles) {
        return userService.changeUserRole(userId, roles);
    }
}
