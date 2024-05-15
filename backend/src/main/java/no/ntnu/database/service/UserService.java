package no.ntnu.database.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import no.ntnu.database.model.Role;
import no.ntnu.database.model.User;
import no.ntnu.database.repository.RoleRepository;
import no.ntnu.database.repository.UserRepository;
import no.ntnu.dto.UserRegistrationDto;
import no.ntnu.dto.UserUpdateDto;
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

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Updates an existing user via its provided id.
     *
     * @param id the id of the {@link User} to update.
     * @param updatedUserDto A {@link UserUpdateDto} contain user info to update.
     *
     * @return the updated {@link User}.
     */
    public User updateUser(Long id, UserUpdateDto updatedUserDto) {
        return userRepository.findById(id)
            .map(user -> {
                user.setId(id);
                user.setUsername(updatedUserDto.getUsername());
                if (updatedUserDto.getPassword() != null && !updatedUserDto.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(updatedUserDto.getPassword()));
                }
                user.setActive(updatedUserDto.isActive());
                if (updatedUserDto.getTwoFactorSecret() != null) {
                    user.setTwoFactorSecret(updatedUserDto.getTwoFactorSecret());
                }
                user.setTwoFactorEnabled(updatedUserDto.isTwoFactorEnabled());

                if (updatedUserDto.getRoles() != null && !updatedUserDto.getRoles().isEmpty()) {
                    Set<Role> roles = new HashSet<>();
                    for (String roleName : updatedUserDto.getRoles()) {
                        Role existingRole = roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                        roles.add(existingRole);
                    }
                    user.setRoles(roles);
                }
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
     * @return True if the user exists, false if not
     */
    public boolean toggleUserActive(Long id, boolean isActive) {
		Optional<User> userOptional = userRepository.findById(id);
		userOptional.ifPresent(user -> {
			user.setActive(isActive);
			userRepository.save(user);
		});
		return userOptional.isPresent();
    }

    
}
