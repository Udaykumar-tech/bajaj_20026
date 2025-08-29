package com.example.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> generateWebhook(String url, String name, String regNo, String email) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("regNo", regNo);
        body.put("email", email);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);
        return response.getBody();
    }

    public String submitAnswer(String url, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("finalQuery", finalQuery);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}
