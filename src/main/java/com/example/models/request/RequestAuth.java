package com.example.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAuth {
    @JsonProperty("username")
    public String username;

    @JsonProperty("password")
    public String password;
}
