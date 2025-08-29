package com.example.demo;

import com.example.demo.service.WebhookService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WebhookServiceTest {

    @Autowired
    private WebhookService webhookService;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGenerateWebhook() {
        // Mock API response for webhook generation
        String mockResponse = "{\"webhook\":\"http://localhost/callback\",\"accessToken\":\"mock-token-123\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        String baseUrl = mockWebServer.url("/hiring/generateWebhook/JAVA").toString();

        Map<String, Object> response = webhookService.generateWebhook(
                baseUrl, "Uday Kumar", "22BCE20026", "test@example.com");

        assertThat(response).containsEntry("accessToken", "mock-token-123");
        assertThat(response).containsEntry("webhook", "http://localhost/callback");
    }

    @Test
    void testSubmitAnswer() {
        // Mock API response for submission
        String mockSubmitResponse = "{\"status\":\"success\",\"message\":\"Query submitted\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockSubmitResponse)
                .addHeader("Content-Type", "application/json"));

        String baseUrl = mockWebServer.url("/hiring/testWebhook/JAVA").toString();

        String response = webhookService.submitAnswer(baseUrl, "mock-token-123", "SELECT 1");

        assertThat(response).contains("Query submitted");
    }
}
