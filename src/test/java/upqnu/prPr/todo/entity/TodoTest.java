package upqnu.prPr.todo.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import upqnu.prPr.member.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class TodoTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() {
        Member memberA = new Member("memberA");
        Member memberB = new Member("memberB");
        em.persist(memberA);
        em.persist(memberA);

        Todo todo1 = new Todo("할일1", memberA);
        Todo todo2 = new Todo("할일2", memberA);
        Todo todo3 = new Todo("할일3", memberB);
        Todo todo4 = new Todo("할일4", memberB);

        em.persist(todo1);
        em.persist(todo2);
        em.persist(todo3);
        em.persist(todo4);

        // 초기화
        em.flush();
        em.clear();

        // 확인
        List<Todo> todos = em.createQuery("select m from Todo m", Todo.class)
                .getResultList();

        for(Todo todo : todos) {
            System.out.println("todo = " + todo);
            System.out.println("member = " + todo.getMember());
        }
    }
}