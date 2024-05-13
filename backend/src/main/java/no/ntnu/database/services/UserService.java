package no.ntnu.database.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import no.ntnu.database.model.Role;
import no.ntnu.database.model.User;
import no.ntnu.database.repositories.RoleRepository;
import no.ntnu.database.repositories.UserRepository;
import no.ntnu.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user data database operations.
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService via Autowired.
     *
     * @param userRepository    the {@link UserRepository} for user entity operations.
     * @param roleRepository    the {@link RoleRepository} for role entity operations.
     * @param passwordEncoder   the {@link PasswordEncoder for encoding passwords.}
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user.
     *
     * @param userDto the data transfer object (Dto)
     *                that contains the necessary user registration details.
     *
     * @return {@link ResponseEntity} with code 200 for success,
     *                                or code 400 for bad request.
     */
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

    /**
     * Updates an existing user via its provided id.
     *
     * @param id the id of the {@link User} to update.
     * @param updatedUser the updated {@link User}.
     *
     * @return the updated {@link User}.
     */
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

    /**
     * Deletes a {@link User} based on the provided id.
     *
     * @param id the id of the {@link User} to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Finds a {@link User} via the provided id.
     *
     * @param id the id of the {@link User}.
     *
     * @return an {@link Optional} of {@link User}.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Toggles the active status of a {@link User}.
     *
     * @param id the id of the {@link User} to toggle.
     * @param isActive the new active status of the {@link User}.
     *
     * @return {@link ResponseEntity} with either code 200 for success,
     *                                or code 404 if not found.
     */
    public ResponseEntity<String> toggleUserActive(Long id, boolean isActive) {
        return userRepository.findById(id)
            .map(user -> {
                user.setActive(isActive);
                userRepository.save(user);
                return ResponseEntity.ok(
                        "User " + (isActive ? "activated" : "deactivated") + " successfully.");
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
}
