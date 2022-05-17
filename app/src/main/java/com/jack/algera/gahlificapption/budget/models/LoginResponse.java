package com.jack.algera.gahlificapption.budget.models;

public class LoginResponse {
    private Long timestamp;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Long timestamp, String token) {
        this.timestamp = timestamp;
        this.token = token;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
