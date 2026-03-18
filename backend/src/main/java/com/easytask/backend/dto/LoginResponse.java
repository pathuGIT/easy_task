package com.easytask.backend.dto;

public class LoginResponse {
    private String active_token;
    private String refresh_token;

    public LoginResponse(String active_token, String refresh_token) {
        this.active_token = active_token;
        this.refresh_token = refresh_token;
    }

    public String getActive_token() {
        return active_token;
    }

    public void setActive_token(String active_token) {
        this.active_token = active_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
