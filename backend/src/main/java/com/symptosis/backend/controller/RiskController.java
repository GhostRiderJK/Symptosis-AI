package com.symptosis.backend.controller;

import com.symptosis.backend.dto.ExplanationResponse;
import com.symptosis.backend.dto.RiskResponse;
import com.symptosis.backend.service.RiskAnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {

    private final RiskAnalysisService riskAnalysisService;

    public RiskController(RiskAnalysisService riskAnalysisService) {
        this.riskAnalysisService = riskAnalysisService;
    }

    @GetMapping("/risk/{patientId}")
    public RiskResponse getRisk(@PathVariable Long patientId) {
        return riskAnalysisService.calculateRisk(patientId);
    }

    @GetMapping("/explanation/{patientId}")
    public ExplanationResponse getExplanation(@PathVariable Long patientId) {
        return riskAnalysisService.explainRisk(patientId);
    }
}
