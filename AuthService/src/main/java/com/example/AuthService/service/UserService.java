package com.example.AuthService.service;

import com.example.AuthService.UserRepository;
import com.example.AuthService.model.User;
import com.example.AuthService.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String registerUser(User user) {
        // Encode the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail());
        user.setPassword(encodedPassword);
        user.setToken(token);

        // Save the user
        userRepository.save(user);

        return token;
    }

    public String authenticateUser(String email) {

        // Generate token
        String token = jwtTokenProvider.createToken(email);

        // Update or save user token
        User user = userRepository.findByEmail(email);
        if (user.getToken() != null) {
            user.setToken(token);
        } else {
            user.setToken(token);
        }

        return token;
    }
}