package com.symptosis.backend.service;

import com.symptosis.backend.config.AppProperties;
import com.symptosis.backend.dto.AnalyticsSnapshot;
import com.symptosis.backend.dto.MlPredictionRequest;
import com.symptosis.backend.dto.MlPredictionResponse;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MlPredictionService {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public MlPredictionService(RestTemplate restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    public Optional<MlPredictionResponse> predict(AnalyticsSnapshot analytics) {
        if (!appProperties.getFeatures().isEnableMl()) {
            return Optional.empty();
        }

        try {
            MlPredictionRequest request = new MlPredictionRequest();
            request.setIncidentSeverity(analytics.getAverageSeverity());
            request.setFrequencyRate(analytics.getAverageFrequency());
            request.setAvgDurationMinutes(analytics.getAverageDuration());
            request.setSystemTier(analytics.getSeverityTrend() >= 7 ? 3 : analytics.getSeverityTrend() >= 4 ? 2 : 1);
            request.setResourceType(analytics.getAverageDuration() >= 30 ? "wearable" : "mobile");
            request.setIsPeakHours(analytics.getAverageFrequency() >= 6 ? 1 : 0);
            request.setSourceSensorId(analytics.getAverageSeverity() >= 6 ? "SNS-HI-01" : "SNS-LO-01");
            request.setIngestionLatencyMs((int) Math.max(120, analytics.getAverageDuration() * 4));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MlPredictionResponse response = restTemplate.postForObject(
                    appProperties.getMl().getApiUrl(),
                    new HttpEntity<>(request, headers),
                    MlPredictionResponse.class
            );
            return Optional.ofNullable(response);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
