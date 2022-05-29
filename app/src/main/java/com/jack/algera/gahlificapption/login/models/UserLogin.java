package com.jack.algera.gahlificapption.login.models;

public class UserLogin {

    private String token;
    private Long timestamp;

    public UserLogin() {
    }

    public UserLogin(String token, Long timestamp) {
        this.token = token;
        this.timestamp = timestamp;
    }

    public boolean isTokenValid() {
        System.out.println("tokenTime: " + timestamp + " - " + System.currentTimeMillis());
        return System.currentTimeMillis() < timestamp;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
