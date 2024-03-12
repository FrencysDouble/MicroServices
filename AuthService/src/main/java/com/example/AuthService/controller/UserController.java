package com.example.AuthService.controller;

import com.example.AuthService.UserRepository;
import com.example.AuthService.model.JwtToken;
import com.example.AuthService.model.User;
import com.example.AuthService.model.UserCredentials;
import com.example.AuthService.security.JwtTokenProvider;
import com.example.AuthService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser( @RequestBody User user, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user data");
        }

        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        }
        try {
            String token = userService.registerUser(user);
            return ResponseEntity.ok(token);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> loginUser(@RequestBody UserCredentials userCredentials) {
        if (userCredentials.getEmail() != null && userCredentials.getPassword() != null) {
            String token = userService.authenticateUser(userCredentials.getEmail(), userCredentials.getPassword());
            if (token != null) {
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String token) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setJwtToken(token);
        if (isValidToken(jwtToken.getJwtToken())) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    private boolean isValidToken(String jwtToken)
    {
        return userRepository.findByToken(jwtToken) != null;
    }
}
