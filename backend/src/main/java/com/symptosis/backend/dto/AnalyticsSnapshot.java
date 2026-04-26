package com.symptosis.backend.dto;

public class AnalyticsSnapshot {
    private double severityTrend;
    private double averageSeverity;
    private double averageFrequency;
    private double averageDuration;
    private int recordCount;

    public double getSeverityTrend() {
        return severityTrend;
    }

    public void setSeverityTrend(double severityTrend) {
        this.severityTrend = severityTrend;
    }

    public double getAverageSeverity() {
        return averageSeverity;
    }

    public void setAverageSeverity(double averageSeverity) {
        this.averageSeverity = averageSeverity;
    }

    public double getAverageFrequency() {
        return averageFrequency;
    }

    public void setAverageFrequency(double averageFrequency) {
        this.averageFrequency = averageFrequency;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
