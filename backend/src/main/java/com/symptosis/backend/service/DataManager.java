package com.symptosis.backend.service;

import com.symptosis.backend.config.AppProperties;
import com.symptosis.backend.dto.RiskResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class DataManager {

    private static final Path AUDIT_FILE = Path.of("..", "logs", "risk-audit.log");
    private static DataManager instance;
    private final Map<Long, RiskResponse> cache = new ConcurrentHashMap<>();
    private final AppProperties appProperties;

    public DataManager(AppProperties appProperties) {
        this.appProperties = appProperties;
        instance = this;
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void cacheRisk(RiskResponse riskResponse) {
        cache.put(riskResponse.getPatientId(), riskResponse);
        if (appProperties.getFeatures().isEnableLogging()) {
            appendAuditLog(riskResponse);
        }
    }

    public Optional<RiskResponse> getCachedRisk(Long patientId) {
        return Optional.ofNullable(cache.get(patientId));
    }

    private void appendAuditLog(RiskResponse riskResponse) {
        try {
            Files.createDirectories(AUDIT_FILE.getParent());
            String line = "%s | patient=%d | risk=%s | score=%.2f | source=%s%n".formatted(
                    LocalDateTime.now(),
                    riskResponse.getPatientId(),
                    riskResponse.getRiskLevel(),
                    riskResponse.getRuleScore(),
                    riskResponse.getDecisionSource()
            );
            Files.writeString(AUDIT_FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
            // Logging should never block analytics.
        }
    }
}
