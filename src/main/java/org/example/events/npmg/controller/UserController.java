package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.Role.ERole;
import org.example.events.npmg.payload.DTOs.UserDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<MessageResponse> changeUserUsername(@PathVariable Long id, @RequestBody String username) {
        return userService.changeUserUsername(id, username);
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<MessageResponse> changeUserBanStatus(@PathVariable Long id) {
        return userService.changeUserBanStatus(id);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<MessageResponse> changeUserRoles(@PathVariable Long id, @RequestBody Set<ERole> roles) {
        return userService.changeUserRoles(id, roles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
