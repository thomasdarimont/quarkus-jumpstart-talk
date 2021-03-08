package com.github.thomasdarimont.training.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

@NotNull
@Schema(title = "TodoNew", description = "The new ToDo")
public class TodoNew {

    @NotEmpty
    private String text;

    @ConstructorProperties("text")
    public TodoNew(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
