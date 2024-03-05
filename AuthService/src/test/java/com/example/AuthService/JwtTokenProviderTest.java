package com.example.AuthService;

import com.example.AuthService.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void testCreateToken() {
        // Given
        String username = "testUser";

        // When
        String token = jwtTokenProvider.createToken(username);

        // Then
        assertNotNull(token);
        System.out.println("Generated token: " + token);
    }
}
