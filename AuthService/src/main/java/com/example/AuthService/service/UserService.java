package com.example.AuthService.service;

import com.example.AuthService.UserRepository;
import com.example.AuthService.model.User;
import com.example.AuthService.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    public String registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        String token = jwtTokenProvider.createToken(user.getEmail());
        user.setPassword(encodedPassword);
        user.setToken(token);

        userRepository.save(user);

        return token;
    }

    public String authenticateUser(String email,String password) {
        try {

            String token = jwtTokenProvider.createToken(email);

            User user = userRepository.findByEmail(email);
            if (user != null)
            {
                String userPass = user.getPassword();
                if (passwordEncoder.matches(password,userPass))
                {
                    user.setToken(token);
                    return token;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}