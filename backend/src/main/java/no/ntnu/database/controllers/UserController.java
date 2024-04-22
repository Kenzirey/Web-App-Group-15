package no.ntnu.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.database.services.UserService;
import no.ntnu.dto.UserRegistrationDto;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
@PostMapping("/register")
public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userDto) {
    return userService.registerNewUser(userDto);
    }
}
