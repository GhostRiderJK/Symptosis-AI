package com.symptosis.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Ml ml = new Ml();
    private final Ollama ollama = new Ollama();
    private final Weights weights = new Weights();
    private final Features features = new Features();

    public Ml getMl() {
        return ml;
    }

    public Ollama getOllama() {
        return ollama;
    }

    public Weights getWeights() {
        return weights;
    }

    public Features getFeatures() {
        return features;
    }

    public static class Ml {
        private String apiUrl;
        private int timeout;

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    public static class Ollama {
        private String baseUrl;
        private String model;
        private int timeout;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    public static class Weights {
        private double severity = 0.5;
        private double frequency = 0.3;
        private double duration = 0.2;

        public double getSeverity() {
            return severity;
        }

        public void setSeverity(double severity) {
            this.severity = severity;
        }

        public double getFrequency() {
            return frequency;
        }

        public void setFrequency(double frequency) {
            this.frequency = frequency;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }
    }

    public static class Features {
        private boolean enableMl = true;
        private boolean enableLlm = true;
        private boolean enableLogging = true;

        public boolean isEnableMl() {
            return enableMl;
        }

        public void setEnableMl(boolean enableMl) {
            this.enableMl = enableMl;
        }

        public boolean isEnableLlm() {
            return enableLlm;
        }

        public void setEnableLlm(boolean enableLlm) {
            this.enableLlm = enableLlm;
        }

        public boolean isEnableLogging() {
            return enableLogging;
        }

        public void setEnableLogging(boolean enableLogging) {
            this.enableLogging = enableLogging;
        }
    }
}
