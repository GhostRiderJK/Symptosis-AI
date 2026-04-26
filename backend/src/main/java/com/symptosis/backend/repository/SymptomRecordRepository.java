package com.symptosis.backend.repository;

import com.symptosis.backend.model.SymptomRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRecordRepository extends JpaRepository<SymptomRecord, Long> {
    List<SymptomRecord> findByPatientIdOrderByTimestampAsc(Long patientId);
}
