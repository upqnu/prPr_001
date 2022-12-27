package upqnu.prPr.todo.repository;

import org.springframework.stereotype.Repository;
import upqnu.prPr.todo.entity.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoMvcRepository {

    private static final Map<Long, Todo> todoGroup = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Todo save(Todo todo) {
        todo.setTodoId(++sequence);
        todoGroup.put(todo.getTodoId(), todo);
        return todo;
    }

    public Todo findById(Long todoId) {
        return todoGroup.get(todoId);
    }

    public List<Todo> findAll() {
        return new ArrayList<>(todoGroup.values());
    }

    public void update(Long todoId, Todo updatedTodo) {
        Todo findTodo = findById(todoId);
        findTodo.setTodoTitle(updatedTodo.getTodoTitle());
        findTodo.setMember(updatedTodo.getMember());
        findTodo.setTodoBody(updatedTodo.getTodoBody());
        // ..
    }

    public void clearTodo() {
        todoGroup.clear();
    }
}
