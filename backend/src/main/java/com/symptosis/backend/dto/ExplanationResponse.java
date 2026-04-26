package com.symptosis.backend.dto;

public class ExplanationResponse {
    private Long patientId;
    private String explanation;
    private String source;
    private RiskResponse risk;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public RiskResponse getRisk() {
        return risk;
    }

    public void setRisk(RiskResponse risk) {
        this.risk = risk;
    }
}
