package com.symptosis.backend.controller;

import com.symptosis.backend.dto.CreateSymptomRecordRequest;
import com.symptosis.backend.dto.SymptomRecordResponse;
import com.symptosis.backend.service.SymptomRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomController {

    private final SymptomRecordService symptomRecordService;

    public SymptomController(SymptomRecordService symptomRecordService) {
        this.symptomRecordService = symptomRecordService;
    }

    @PostMapping("/symptoms")
    @ResponseStatus(HttpStatus.CREATED)
    public SymptomRecordResponse addSymptomRecord(@Valid @RequestBody CreateSymptomRecordRequest request) {
        return symptomRecordService.create(request);
    }
}
