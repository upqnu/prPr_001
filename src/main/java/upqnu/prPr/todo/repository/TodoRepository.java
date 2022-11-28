package upqnu.prPr.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import upqnu.prPr.todo.dto.TodoDto;
import upqnu.prPr.todo.entity.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByTodoTitleAndTodoBody(String todoTitle, String todoBody);

    @Query("select new upqnu.prPr.todo.dto.TodoDto(t.todoId, t.todoTitle, m.name)" + "from Todo t join t.member m")
    List<TodoDto> findTodoDto();
}
