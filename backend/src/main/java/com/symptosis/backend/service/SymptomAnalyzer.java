package com.symptosis.backend.service;

import com.symptosis.backend.dto.AnalyticsSnapshot;
import com.symptosis.backend.model.SymptomRecord;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SymptomAnalyzer {

    public AnalyticsSnapshot analyze(List<SymptomRecord> records) {
        AnalyticsSnapshot snapshot = new AnalyticsSnapshot();
        if (records == null || records.isEmpty()) {
            return snapshot;
        }

        double avgSeverity = records.stream().mapToInt(SymptomRecord::getSeverity).average().orElse(0.0);
        double avgFrequency = records.stream().mapToInt(SymptomRecord::getFrequency).average().orElse(0.0);
        double avgDuration = records.stream().mapToInt(SymptomRecord::getDurationMinutes).average().orElse(0.0);

        int firstSeverity = records.get(0).getSeverity();
        int lastSeverity = records.get(records.size() - 1).getSeverity();
        double severityTrend = records.size() == 1 ? lastSeverity : (double) (lastSeverity - firstSeverity + lastSeverity) / 2.0;

        snapshot.setAverageSeverity(avgSeverity);
        snapshot.setAverageFrequency(avgFrequency);
        snapshot.setAverageDuration(avgDuration);
        snapshot.setSeverityTrend(severityTrend);
        snapshot.setRecordCount(records.size());
        return snapshot;
    }
}
