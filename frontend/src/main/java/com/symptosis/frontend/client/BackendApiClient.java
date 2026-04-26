package com.symptosis.frontend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.symptosis.frontend.model.ExplanationView;
import com.symptosis.frontend.model.PatientView;
import com.symptosis.frontend.model.RiskView;
import com.symptosis.frontend.model.SymptomRecordView;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class BackendApiClient {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .build();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final String baseUrl;

    public BackendApiClient() {
        this.baseUrl = "http://localhost:8080";
    }

    public PatientView createPatient(Map<String, Object> payload) throws IOException, InterruptedException {
        return sendPost("/patients", payload, PatientView.class);
    }

    public SymptomRecordView addSymptom(Map<String, Object> payload) throws IOException, InterruptedException {
        return sendPost("/symptoms", payload, SymptomRecordView.class);
    }

    public PatientView getPatient(long patientId) throws IOException, InterruptedException {
        return sendGet("/patients/" + patientId, PatientView.class);
    }

    public RiskView getRisk(long patientId) throws IOException, InterruptedException {
        return sendGet("/risk/" + patientId, RiskView.class);
    }

    public ExplanationView getExplanation(long patientId) throws IOException, InterruptedException {
        return sendGet("/explanation/" + patientId, ExplanationView.class);
    }

    private <T> T sendPost(String path, Object payload, Class<T> responseType) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(REQUEST_TIMEOUT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new IOException(response.body());
        }
        return mapper.readValue(response.body(), responseType);
    }

    private <T> T sendGet(String path, Class<T> responseType) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(REQUEST_TIMEOUT)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new IOException(response.body());
        }
        return mapper.readValue(response.body(), responseType);
    }
}
