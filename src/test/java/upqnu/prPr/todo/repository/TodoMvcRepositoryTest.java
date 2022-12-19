package upqnu.prPr.todo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import upqnu.prPr.member.entity.Member;
import upqnu.prPr.todo.entity.Todo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TodoMvcRepositoryTest {

    TodoMvcRepository todoMvcRepository = new TodoMvcRepository();

    @AfterEach
    void afterEach() {
        todoMvcRepository.clearTodo();
    }

    @Test
    void save() {
        Member member = new Member("aaa", "aaa@gmail.com");
        Todo todo = new Todo("title1", member);

        Todo savedTodo = todoMvcRepository.save(todo);

        Todo findTodo = todoMvcRepository.findById(todo.getTodoId());
        assertThat(findTodo).isEqualTo(savedTodo);
    }

    @Test
    void findById() {
        Member member = new Member("aaa", "aaa@gmail.com");
        Todo todo = new Todo("title1", member);
        Todo savedTodo = todoMvcRepository.save(todo);

        Todo findTodo = todoMvcRepository.findById(savedTodo.getTodoId());

        assertThat(findTodo.getTodoTitle()).isEqualTo(savedTodo.getTodoTitle());
        assertThat(findTodo.getMember()).isEqualTo(savedTodo.getMember());
        assertThat(findTodo).isEqualTo(todo);
    }

    @Test
    void findAll() {
        Member member1 = new Member("aaa", "aaa.gmail.com");
        Todo todo1 = new Todo("title1", member1);
        Todo todo2 = new Todo("title2", member1);

        Todo savedTodo1 = todoMvcRepository.save(todo1);
        Todo savedTodo2 = todoMvcRepository.save(todo2);

        List<Todo> todos = todoMvcRepository.findAll();

        assertThat(todos.size()).isEqualTo(2);
        assertThat(todos).contains(todo1, todo2);
    }

    @Test
    void updateTodo() {
        Member member = new Member("aaa", "aaa.gmail.com");
        Todo todo = new Todo("title1", member);
        Todo savedTodo = todoMvcRepository.save(todo);
        Long todoId = savedTodo.getTodoId();

        Member member2 = new Member("bbb", "bbb@gmail.com");
        Todo updatedTodo = new Todo("title2", member2);
        todoMvcRepository.update(todoId, updatedTodo);

        Todo findTodo = todoMvcRepository.findById(todoId);
        assertThat(findTodo.getTodoTitle()).isEqualTo(updatedTodo.getTodoTitle());
        assertThat(findTodo.getMember()).isEqualTo(updatedTodo.getMember());
    }
}