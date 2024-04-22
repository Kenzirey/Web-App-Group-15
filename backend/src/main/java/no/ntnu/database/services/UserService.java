package no.ntnu.database.services;

import java.util.HashSet;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import no.ntnu.database.entities.Role;
import no.ntnu.database.entities.User;
import no.ntnu.database.repositories.UserRepository;
import no.ntnu.dto.UserRegistrationDto;
import no.ntnu.database.repositories.RoleRepository;

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
}