package com.symptosis.backend.service;

import com.symptosis.backend.dto.CreateSymptomRecordRequest;
import com.symptosis.backend.dto.SymptomRecordResponse;
import com.symptosis.backend.model.Patient;
import com.symptosis.backend.model.Symptom;
import com.symptosis.backend.model.SymptomRecord;
import com.symptosis.backend.repository.SymptomRecordRepository;
import com.symptosis.backend.repository.SymptomRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class SymptomRecordService {

    private final PatientService patientService;
    private final SymptomRepository symptomRepository;
    private final SymptomRecordRepository symptomRecordRepository;

    public SymptomRecordService(PatientService patientService,
                                SymptomRepository symptomRepository,
                                SymptomRecordRepository symptomRecordRepository) {
        this.patientService = patientService;
        this.symptomRepository = symptomRepository;
        this.symptomRecordRepository = symptomRecordRepository;
    }

    public SymptomRecordResponse create(CreateSymptomRecordRequest request) {
        Patient patient = patientService.getPatientEntity(request.getPatientId());
        Symptom symptom = symptomRepository.findByNameIgnoreCase(request.getSymptomName())
                .orElseGet(() -> {
                    Symptom newSymptom = new Symptom();
                    newSymptom.setName(request.getSymptomName());
                    newSymptom.setCategory(request.getSymptomCategory());
                    return symptomRepository.save(newSymptom);
                });

        SymptomRecord record = new SymptomRecord();
        record.setPatient(patient);
        record.setSymptom(symptom);
        record.setSeverity(request.getSeverity());
        record.setFrequency(request.getFrequency());
        record.setDurationMinutes(request.getDurationMinutes());
        record.setTimestamp(request.getTimestamp() == null ? LocalDateTime.now() : request.getTimestamp());
        record.setNote(request.getNote());

        SymptomRecord saved = symptomRecordRepository.save(record);
        patient.getSymptomRecords().add(saved);
        return patientService.mapRecord(saved);
    }
}
