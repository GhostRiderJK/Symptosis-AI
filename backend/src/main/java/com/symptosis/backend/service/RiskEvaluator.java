package com.symptosis.backend.service;

import com.symptosis.backend.config.AppProperties;
import com.symptosis.backend.dto.AnalyticsSnapshot;
import com.symptosis.backend.model.RiskLevel;
import org.springframework.stereotype.Component;

@Component
public class RiskEvaluator {

    private final AppProperties appProperties;

    public RiskEvaluator(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public double calculateRuleScore(AnalyticsSnapshot analytics) {
        return (analytics.getSeverityTrend() * appProperties.getWeights().getSeverity())
                + (analytics.getAverageFrequency() * appProperties.getWeights().getFrequency())
                + (analytics.getAverageDuration() * appProperties.getWeights().getDuration()) / 10.0;
    }

    public RiskLevel classify(double score) {
        if (score >= 7.5) {
            return RiskLevel.HIGH;
        }
        if (score >= 4.0) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }
}
