package com.symptosis.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    private final ConfigurableEnvironment environment;
    private Dotenv dotenv;

    public AppConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadEnv() {
        dotenv = Dotenv.configure()
                .directory("../")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        Map<String, Object> values = new LinkedHashMap<>();
        put(values, "server.port", "SERVER_PORT");
        put(values, "spring.datasource.url", "DB_URL");
        put(values, "spring.datasource.username", "DB_USER");
        put(values, "spring.datasource.password", "DB_PASSWORD");
        put(values, "app.ml.api-url", "ML_API_URL");
        put(values, "app.ml.timeout", "ML_TIMEOUT");
        put(values, "app.ollama.base-url", "OLLAMA_BASE_URL");
        put(values, "app.ollama.model", "OLLAMA_MODEL");
        put(values, "app.ollama.timeout", "OLLAMA_TIMEOUT");
        put(values, "app.weights.severity", "WEIGHT_SEVERITY");
        put(values, "app.weights.frequency", "WEIGHT_FREQUENCY");
        put(values, "app.weights.duration", "WEIGHT_DURATION");
        put(values, "app.features.enable-ml", "ENABLE_ML");
        put(values, "app.features.enable-llm", "ENABLE_LLM");
        put(values, "app.features.enable-logging", "ENABLE_LOGGING");
        environment.getPropertySources().addFirst(new MapPropertySource("dotenvProperties", values));
    }

    private void put(Map<String, Object> values, String key, String envKey) {
        String value = dotenv.get(envKey);
        if (value != null && !value.isBlank()) {
            values.put(key, value);
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        int timeout = Integer.parseInt(environment.getProperty("app.ml.timeout", "3000"));
        int ollamaTimeout = Integer.parseInt(environment.getProperty("app.ollama.timeout", "5000"));
        int maxTimeout = Math.max(timeout, ollamaTimeout);
        return builder
                .setConnectTimeout(Duration.ofMillis(maxTimeout))
                .setReadTimeout(Duration.ofMillis(maxTimeout))
                .build();
    }
}
