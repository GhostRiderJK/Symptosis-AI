package com.symptosis.backend.service;

import com.symptosis.backend.config.AppProperties;
import com.symptosis.backend.dto.AnalyticsSnapshot;
import com.symptosis.backend.dto.RiskResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaService {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public OllamaService(RestTemplate restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    public String generateExplanation(RiskResponse riskResponse) {
        AnalyticsSnapshot analytics = riskResponse.getAnalytics();
        String fallback = String.format(
                "Risk level %s was assigned because the severity trend is %.2f, average frequency is %.2f, and average duration is %.2f minutes. This project provides symptom analytics only and does not diagnose disease.",
                riskResponse.getRiskLevel(),
                analytics.getSeverityTrend(),
                analytics.getAverageFrequency(),
                analytics.getAverageDuration()
        );

        if (!appProperties.getFeatures().isEnableLlm()) {
            return fallback;
        }

        try {
            String prompt = """
                    Patient data:
                    Severity trend: %s
                    Frequency: %s
                    Duration: %s
                    Risk: %s

                    Explain clearly why this risk level is assigned.
                    """.formatted(
                    analytics.getSeverityTrend(),
                    analytics.getAverageFrequency(),
                    analytics.getAverageDuration(),
                    riskResponse.getRiskLevel()
            );

            Map<String, Object> body = new HashMap<>();
            body.put("model", appProperties.getOllama().getModel());
            body.put("prompt", prompt);
            body.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(
                    appProperties.getOllama().getBaseUrl() + "/api/generate",
                    new HttpEntity<>(body, headers),
                    Map.class
            );
            if (response != null && response.get("response") != null) {
                return response.get("response").toString().trim();
            }
            return fallback;
        } catch (Exception ex) {
            return fallback;
        }
    }
}
