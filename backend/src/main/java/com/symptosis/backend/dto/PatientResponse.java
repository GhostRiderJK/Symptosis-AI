package com.symptosis.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class PatientResponse {
    private Long id;
    private String fullName;
    private String patientCode;
    private int age;
    private String gender;
    private LocalDate registeredDate;
    private List<SymptomRecordResponse> symptomHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public List<SymptomRecordResponse> getSymptomHistory() {
        return symptomHistory;
    }

    public void setSymptomHistory(List<SymptomRecordResponse> symptomHistory) {
        this.symptomHistory = symptomHistory;
    }
}
