package com.symptosis.backend.repository;

import com.symptosis.backend.model.Symptom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    Optional<Symptom> findByNameIgnoreCase(String name);
}
