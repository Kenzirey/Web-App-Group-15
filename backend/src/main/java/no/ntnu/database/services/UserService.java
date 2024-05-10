package no.ntnu.database.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import no.ntnu.database.entities.Role;
import no.ntnu.database.entities.User;
import no.ntnu.database.repositories.RoleRepository;
import no.ntnu.database.repositories.UserRepository;
import no.ntnu.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerNewUser(UserRegistrationDto userDto) {
        if (userRepository.existsByUsername(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Username is already in use!");
        }

        User newUser = new User();
        newUser.setUsername(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setActive(true);
        newUser.setTwoFactorEnabled(userDto.isTwoFactorEnabled());

        Role userRole = roleRepository.findByName(userDto.getRole())
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
            .map(user -> {
                user.setUsername(updatedUser.getUsername());
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                user.setActive(updatedUser.isActive());
                user.setRoles(updatedUser.getRoles());
                user.setTwoFactorEnabled(updatedUser.isTwoFactorEnabled());
                user.setTwoFactorSecret(updatedUser.getTwoFactorSecret());
                return userRepository.save(user);
            })
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<String> toggleUserActive(Long id, boolean isActive) {
        return userRepository.findById(id)
            .map(user -> {
                user.setActive(isActive);
                userRepository.save(user);
                return ResponseEntity.ok("User " + (isActive ? "activated" : "deactivated") + " successfully.");
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
}
