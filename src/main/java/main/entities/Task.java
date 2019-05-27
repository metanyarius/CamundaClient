package main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private String name;
    private String created;
    private String taskDefinitionKey;

    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }
}
