package com.example.demo.runner;

import com.example.demo.model.SqlSolution;
import com.example.demo.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

    private final WebhookService webhookService;
    private static final String NAME = "Uday Kumar";
    private static final String REG_NO = "22BCE20026";
    private static final String EMAIL = "your-email@example.com";

    public AppRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) {
        try {
            log.info("🚀 Starting submission workflow for {}", REG_NO);

            // Step 1: Generate webhook
            String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
            Map<String, Object> webhookResponse = webhookService.generateWebhook(url, NAME, REG_NO, EMAIL);

            String webhook = (String) webhookResponse.get("webhook");
            String accessToken = (String) webhookResponse.get("accessToken");

            log.info("✅ Webhook: {}", webhook);
            log.info("✅ Access token acquired.");

            // Step 2: Pick query based on regNo
            String finalQuery = SqlSolution.getSolution(REG_NO);
            log.info("📄 SQL solution prepared for regNo {}: {}", REG_NO, finalQuery);

            // Step 3: Submit
            String submitUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
            String response = webhookService.submitAnswer(submitUrl, accessToken, finalQuery);

            log.info("📤 Submission response: {}", response);
            log.info("🎉 Workflow completed successfully!");

        } catch (Exception ex) {
            log.error("❌ Workflow failed: {}", ex.getMessage(), ex);
        }
    }
}
