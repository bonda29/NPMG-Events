package org.example.events.npmg.service;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.Role.Role;
import org.example.events.npmg.models.User;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<MessageResponse> changeUserUsername(Long userId, String username) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        User user = findById(userRepository, userId);
        user.setUsername(username);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User's username has been changed successfully!"));
    }

    public ResponseEntity<MessageResponse> changeUserBanStatus(Long userId) {
        User user = findById(userRepository, userId);
        user.setBanned(!user.isBanned());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User's ban status has been changed successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok(new MessageResponse("User has been deleted successfully!"));
    }

    public ResponseEntity<MessageResponse> changeUserRole(Long userId, Set<Role> roles) {

        User user = findById(userRepository, userId);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User's role has been changed successfully!"));
    }

//    public ResponseEntity<MessageResponse> changeUserPassword(Long userId, String password) {
//        return userRepository.findById(userId)
//                .map(user -> {
//                    user.setPassword(password);
//                    userRepository.save(user);
//                    return ResponseEntity.ok(new MessageResponse("User's password has been changed successfully!"));
//                })
//                .orElseGet(() -> ResponseEntity.badRequest().body(new MessageResponse("User not found!")));
//    }


}
