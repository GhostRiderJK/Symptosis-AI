package com.symptosis.backend.service;

import com.symptosis.backend.dto.CreatePatientRequest;
import com.symptosis.backend.dto.PatientResponse;
import com.symptosis.backend.dto.SymptomRecordResponse;
import com.symptosis.backend.model.Patient;
import com.symptosis.backend.model.SymptomRecord;
import com.symptosis.backend.repository.PatientRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse createPatient(CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setFullName(request.getFullName());
        patient.setPatientCode(request.getPatientCode());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        return map(patientRepository.save(patient));
    }

    public Patient getPatientEntity(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
    }

    public PatientResponse getPatient(Long patientId) {
        return map(getPatientEntity(patientId));
    }

    public PatientResponse map(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setFullName(patient.getFullName());
        response.setPatientCode(patient.getPatientCode());
        response.setAge(patient.getAge());
        response.setGender(patient.getGender());
        response.setRegisteredDate(patient.getRegisteredDate());
        List<SymptomRecordResponse> history = patient.getSymptomRecords().stream()
                .sorted(Comparator.comparing(SymptomRecord::getTimestamp))
                .map(this::mapRecord)
                .toList();
        response.setSymptomHistory(history);
        return response;
    }

    public SymptomRecordResponse mapRecord(SymptomRecord record) {
        SymptomRecordResponse response = new SymptomRecordResponse();
        response.setId(record.getId());
        response.setSymptomName(record.getSymptom().getName());
        response.setSymptomCategory(record.getSymptom().getCategory());
        response.setSeverity(record.getSeverity());
        response.setFrequency(record.getFrequency());
        response.setDurationMinutes(record.getDurationMinutes());
        response.setTimestamp(record.getTimestamp());
        response.setNote(record.getNote());
        return response;
    }
}
