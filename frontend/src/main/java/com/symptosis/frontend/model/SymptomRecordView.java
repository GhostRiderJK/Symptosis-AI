package com.symptosis.frontend.model;

import java.time.LocalDateTime;

public class SymptomRecordView {
    public Long id;
    public String symptomName;
    public String symptomCategory;
    public int severity;
    public int frequency;
    public int durationMinutes;
    public LocalDateTime timestamp;
    public String note;
}
