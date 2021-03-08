package com.github.thomasdarimont.training.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(title = "Todo", description = "The ToDo data")
public class Todo extends TodoUpdate {

    private final Long id;

    public Todo(Long id, String text) {
        this(id, text, false);
    }

    public Todo(Long id, String text, Boolean completed) {
        super(text, completed);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
