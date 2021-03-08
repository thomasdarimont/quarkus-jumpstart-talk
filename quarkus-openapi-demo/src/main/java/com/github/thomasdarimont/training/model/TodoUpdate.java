package com.github.thomasdarimont.training.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.beans.ConstructorProperties;

@Schema(title = "TodoUpdate", description = "An update for a ToDo")
public class TodoUpdate extends TodoNew {

    private Boolean completed;

    @ConstructorProperties({"text", "completed"})
    public TodoUpdate(String text, Boolean completed) {
        super(text);
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
