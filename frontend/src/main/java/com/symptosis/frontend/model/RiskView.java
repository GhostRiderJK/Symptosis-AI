package com.symptosis.frontend.model;

public class RiskView {
    public Long patientId;
    public String riskLevel;
    public double ruleScore;
    public String mlPrediction;
    public String decisionSource;
    public AnalyticsView analytics;
}
