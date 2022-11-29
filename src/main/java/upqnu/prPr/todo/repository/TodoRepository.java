package upqnu.prPr.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import upqnu.prPr.todo.dto.TodoDto;
import upqnu.prPr.todo.entity.Todo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByTodoTitleAndTodoBody(String todoTitle, String todoBody);

    @Query("select t from Todo t where t.todoTitle = :todoTitle and t.todoBody = :todoBody")
    List<Todo> findTodo(@Param("todoTitle") String todoTitle, @Param("todoBody") String todoBody);

    @Query("select t.todoTitle from Todo t")
    List<Todo> findTodoTitleList();

    @Query("select new upqnu.prPr.todo.dto.TodoDto(t.todoId, t.todoTitle, m.name)" + "from Todo t join t.member m")
    List<TodoDto> findTodoDto();

    @Query("select t from Todo t where t.todoTitle in :titles")
    List<Todo> findByTitles(@Param("titles") Collection<String> titles);

    // 아래 3개 메서드는 Spring Data JPA의 반환타입이 유연함을 보여줌.
    List<Todo> findListByTodoTitle(String todoTitle); //컬렉션

    Todo findTodoByTodoTitle(String todoTitle); //단건

    Optional<Todo> findOptionalByTodoTitle(String todoTitle); //단건 Optional

    Page<Todo> findByTodoBody(String todoBody, Pageable pageable); //
}
