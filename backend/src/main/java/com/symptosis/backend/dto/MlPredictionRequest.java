package com.symptosis.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MlPredictionRequest {
    @JsonProperty("incident_severity")
    private double incidentSeverity;

    @JsonProperty("frequency_rate")
    private double frequencyRate;

    @JsonProperty("avg_duration_minutes")
    private double avgDurationMinutes;

    @JsonProperty("system_tier")
    private int systemTier;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("is_peak_hours")
    private int isPeakHours;

    @JsonProperty("source_sensor_id")
    private String sourceSensorId;

    @JsonProperty("ingestion_latency_ms")
    private int ingestionLatencyMs;

    public double getIncidentSeverity() {
        return incidentSeverity;
    }

    public void setIncidentSeverity(double incidentSeverity) {
        this.incidentSeverity = incidentSeverity;
    }

    public double getFrequencyRate() {
        return frequencyRate;
    }

    public void setFrequencyRate(double frequencyRate) {
        this.frequencyRate = frequencyRate;
    }

    public double getAvgDurationMinutes() {
        return avgDurationMinutes;
    }

    public void setAvgDurationMinutes(double avgDurationMinutes) {
        this.avgDurationMinutes = avgDurationMinutes;
    }

    public int getSystemTier() {
        return systemTier;
    }

    public void setSystemTier(int systemTier) {
        this.systemTier = systemTier;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public int getIsPeakHours() {
        return isPeakHours;
    }

    public void setIsPeakHours(int isPeakHours) {
        this.isPeakHours = isPeakHours;
    }

    public String getSourceSensorId() {
        return sourceSensorId;
    }

    public void setSourceSensorId(String sourceSensorId) {
        this.sourceSensorId = sourceSensorId;
    }

    public int getIngestionLatencyMs() {
        return ingestionLatencyMs;
    }

    public void setIngestionLatencyMs(int ingestionLatencyMs) {
        this.ingestionLatencyMs = ingestionLatencyMs;
    }
}
