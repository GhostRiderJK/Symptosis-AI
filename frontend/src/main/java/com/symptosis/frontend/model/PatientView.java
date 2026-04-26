package com.symptosis.frontend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientView {
    public Long id;
    public String fullName;
    public String patientCode;
    public int age;
    public String gender;
    public LocalDate registeredDate;
    public List<SymptomRecordView> symptomHistory = new ArrayList<>();
}
