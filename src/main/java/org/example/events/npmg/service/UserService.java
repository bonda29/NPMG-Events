package org.example.events.npmg.service;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.config.Mapper.UserMapper;
import org.example.events.npmg.models.Role.ERole;
import org.example.events.npmg.models.Role.Role;
import org.example.events.npmg.models.User;
import org.example.events.npmg.payload.DTOs.UserDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.RoleRepository;
import org.example.events.npmg.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public ResponseEntity<UserDto> getUserById(Long userId) {
        User user = findById(userRepository, userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userMapper.toDto(users);
        return ResponseEntity.ok(userDtos);
    }

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

    public ResponseEntity<MessageResponse> changeUserRoles(Long userId, Set<ERole> roleNames) {
        Optional<Set<Role>> roles = roleRepository.findByNameIn(roleNames);
        if (roles.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Roles not found!"));
        } else if (roles.get().size() != roleNames.size()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Some roles not found!"));
        }

        User user = findById(userRepository, userId);
        user.setRoles(roles.get());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User's roles has been changed successfully!"));
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
