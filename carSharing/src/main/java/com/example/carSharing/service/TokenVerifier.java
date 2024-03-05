package com.example.carSharing.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TokenVerifier {

    private final RestTemplate restTemplate;

    public TokenVerifier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean verify(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String authService = "http://localhost:8080/api/v1/user/verify-token";
        ResponseEntity<String> response = restTemplate.exchange(authService, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }
}
