package com.github.thomasdarimont.training;

import com.github.thomasdarimont.training.model.Todo;
import com.github.thomasdarimont.training.model.TodoUpdate;
import com.github.thomasdarimont.training.model.jpa.TodoEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;


@ApplicationScoped
@Transactional(REQUIRED)
public class TodoService {

    @Transactional(SUPPORTS)
    public List<Todo> findAll() {
        return TodoEntity.<TodoEntity>listAll()
                .stream()
                .map(this::toTodo)
                .collect(Collectors.toList());
    }

    @Transactional(SUPPORTS)
    public Optional<Todo> findById(Long id) {
        return TodoEntity.<TodoEntity>findByIdOptional(id)
                .map(this::toTodo);
    }

    public long create(String text) {
        TodoEntity entity = new TodoEntity();
        entity.setText(text);
        entity.persist();
        return entity.id;
    }

    public boolean delete(Long id) {
        return TodoEntity.deleteById(id);
    }

    public Optional<Todo> update(Long id, TodoUpdate update) {
        return TodoEntity.<TodoEntity>findByIdOptional(id).map(entity -> update(update, entity));
    }

    private Todo update(TodoUpdate update, TodoEntity entity) {

        if (update.getCompleted() != null) {
            entity.setCompleted(update.getCompleted());
        }
        entity.setText(update.getText());

        return new Todo(entity.id, entity.getText(), entity.getCompleted());
    }

    private Todo toTodo(TodoEntity entity) {
        return new Todo(entity.id, entity.getText(), entity.getCompleted());
    }
}
