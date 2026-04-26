package com.symptosis.backend.dto;

import com.symptosis.backend.model.RiskLevel;

public class RiskResponse {
    private Long patientId;
    private RiskLevel riskLevel;
    private double ruleScore;
    private String mlPrediction;
    private String decisionSource;
    private AnalyticsSnapshot analytics;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public double getRuleScore() {
        return ruleScore;
    }

    public void setRuleScore(double ruleScore) {
        this.ruleScore = ruleScore;
    }

    public String getMlPrediction() {
        return mlPrediction;
    }

    public void setMlPrediction(String mlPrediction) {
        this.mlPrediction = mlPrediction;
    }

    public String getDecisionSource() {
        return decisionSource;
    }

    public void setDecisionSource(String decisionSource) {
        this.decisionSource = decisionSource;
    }

    public AnalyticsSnapshot getAnalytics() {
        return analytics;
    }

    public void setAnalytics(AnalyticsSnapshot analytics) {
        this.analytics = analytics;
    }
}
