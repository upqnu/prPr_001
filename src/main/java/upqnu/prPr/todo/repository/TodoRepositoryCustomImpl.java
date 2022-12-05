package upqnu.prPr.todo.repository;

import lombok.RequiredArgsConstructor;
import upqnu.prPr.todo.entity.Todo;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final EntityManager em; // 순수 JPA로 커스텀 구현

    public List<Todo> findTodoCustom1(){
        return em.createQuery("select t from Todo t")
                .getResultList();
    }
}
