package com.symptosis.backend.service;

import com.symptosis.backend.dto.AnalyticsSnapshot;
import com.symptosis.backend.dto.ExplanationResponse;
import com.symptosis.backend.dto.MlPredictionResponse;
import com.symptosis.backend.dto.RiskResponse;
import com.symptosis.backend.model.RiskLevel;
import com.symptosis.backend.model.SymptomRecord;
import com.symptosis.backend.repository.SymptomRecordRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalysisService {

    private final SymptomRecordRepository symptomRecordRepository;
    private final SymptomAnalyzer symptomAnalyzer;
    private final RiskEvaluator riskEvaluator;
    private final MlPredictionService mlPredictionService;
    private final OllamaService ollamaService;
    private final DataManager dataManager;

    public RiskAnalysisService(SymptomRecordRepository symptomRecordRepository,
                               SymptomAnalyzer symptomAnalyzer,
                               RiskEvaluator riskEvaluator,
                               MlPredictionService mlPredictionService,
                               OllamaService ollamaService,
                               DataManager dataManager) {
        this.symptomRecordRepository = symptomRecordRepository;
        this.symptomAnalyzer = symptomAnalyzer;
        this.riskEvaluator = riskEvaluator;
        this.mlPredictionService = mlPredictionService;
        this.ollamaService = ollamaService;
        this.dataManager = dataManager;
    }

    public RiskResponse calculateRisk(Long patientId) {
        List<SymptomRecord> records = symptomRecordRepository.findByPatientIdOrderByTimestampAsc(patientId);
        if (records.isEmpty()) {
            throw new IllegalArgumentException("No symptom records found for patient: " + patientId);
        }

        AnalyticsSnapshot analytics = symptomAnalyzer.analyze(records);
        double score = riskEvaluator.calculateRuleScore(analytics);
        RiskLevel finalRisk = riskEvaluator.classify(score);
        Optional<MlPredictionResponse> mlResponse = mlPredictionService.predict(analytics);

        String decisionSource = "RULE_BASED";
        if (mlResponse.isPresent()) {
            String predicted = mlResponse.get().getRiskCategory().toUpperCase(Locale.ROOT);
            try {
                RiskLevel mlRisk = RiskLevel.valueOf(predicted);
                if (mlRisk.ordinal() > finalRisk.ordinal()) {
                    finalRisk = mlRisk;
                }
                decisionSource = "HYBRID_RULE_ML";
            } catch (IllegalArgumentException ignored) {
                decisionSource = "RULE_BASED";
            }
        }

        RiskResponse response = new RiskResponse();
        response.setPatientId(patientId);
        response.setRiskLevel(finalRisk);
        response.setRuleScore(Math.round(score * 100.0) / 100.0);
        response.setMlPrediction(mlResponse.map(MlPredictionResponse::getRiskCategory).orElse("UNAVAILABLE"));
        response.setDecisionSource(decisionSource);
        response.setAnalytics(analytics);
        dataManager.cacheRisk(response);
        return response;
    }

    public ExplanationResponse explainRisk(Long patientId) {
        RiskResponse riskResponse = dataManager.getCachedRisk(patientId).orElseGet(() -> calculateRisk(patientId));
        String explanation = ollamaService.generateExplanation(riskResponse);

        ExplanationResponse response = new ExplanationResponse();
        response.setPatientId(patientId);
        response.setExplanation(explanation);
        response.setSource(explanation.contains("does not diagnose disease") ? "STATIC_FALLBACK" : "OLLAMA_OR_FALLBACK");
        response.setRisk(riskResponse);
        return response;
    }
}
