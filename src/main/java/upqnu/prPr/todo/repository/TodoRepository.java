package upqnu.prPr.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upqnu.prPr.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
