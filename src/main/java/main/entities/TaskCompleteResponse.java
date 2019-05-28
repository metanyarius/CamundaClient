package main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskCompleteResponse {
    private String status;

    public String getStatus() {
        return status;
    }
}
