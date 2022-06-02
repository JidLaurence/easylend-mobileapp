package com.example.myapplication;

import java.lang.reflect.Array;

public class User {
    private String apiToken;
    private String email;
    private String password;

    public User(String apiToken, String email, String password) {
        this.apiToken = apiToken;
        this.email = email;
        this.password = password;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
