package com.symptosis.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateSymptomRecordRequest {

    @NotNull
    private Long patientId;

    @NotBlank
    private String symptomName;

    @NotBlank
    private String symptomCategory;

    @Min(1)
    @Max(10)
    private int severity;

    @Min(1)
    @Max(20)
    private int frequency;

    @Min(1)
    @Max(1440)
    private int durationMinutes;

    private LocalDateTime timestamp;
    private String note;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getSymptomCategory() {
        return symptomCategory;
    }

    public void setSymptomCategory(String symptomCategory) {
        this.symptomCategory = symptomCategory;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
