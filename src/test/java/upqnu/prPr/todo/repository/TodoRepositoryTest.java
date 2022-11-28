package upqnu.prPr.todo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import upqnu.prPr.member.entity.Member;
import upqnu.prPr.member.repository.MemberRepository;
import upqnu.prPr.todo.dto.TodoDto;
import upqnu.prPr.todo.entity.Todo;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoRepositoryTest {

    @Autowired TodoRepository todoRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void testTodo() {
        Todo todo1 = new Todo("title1", "body1");
        Todo savedTodo = todoRepository.save(todo1);

        Todo findTodo = todoRepository.findById(savedTodo.getTodoId()).get(); // id가 null이면 예외 발생하므로 원래 orElse와 함께 써야 한다.

        assertThat(findTodo.getTodoId()).isEqualTo(todo1.getTodoId());
        assertThat(findTodo.getTodoTitle()).isEqualTo(todo1.getTodoTitle());
        assertThat(findTodo.getTodoBody()).isEqualTo(todo1.getTodoBody());
        assertThat(findTodo).isEqualTo(todo1);
    }

    @Test
    public void basicCRUD() {
        Todo todo1 = new Todo("title1", "body1");
        Todo todo2 = new Todo("title2", "body2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        //단건 조회 검증
        Todo findTodo1 = todoRepository.findById(todo1.getTodoId()).get();
        Todo findTodo2 = todoRepository.findById(todo2.getTodoId()).get();

        assertThat(findTodo1).isEqualTo(todo1);
        assertThat(findTodo2).isEqualTo(todo2);

        //리스트 조회 검증
        List<Todo> all = todoRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = todoRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증 memberRepository.delete(member1); memberRepository.delete(member2);
        todoRepository.delete(todo1);
        todoRepository.delete(todo2);
        long deletedCount = todoRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByTodoTitleAndTodoBody() {
        Todo todo1 = new Todo("title1", "body1");
        Todo todo2 = new Todo("title2", "body2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        List<Todo> result = todoRepository.findByTodoTitleAndTodoBody("title1", "body1");
        assertThat(result.get(0).getTodoTitle()).isEqualTo("title1");
        assertThat(result.get(0).getTodoBody()).isEqualTo("body1");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findTodoDto() {
        Member member = new Member("memberA");
        memberRepository.save(member);

        Todo todo1 = new Todo("title1", "body1");
        todo1.setMember(member);
        todoRepository.save(todo1);

        List<TodoDto> todoDto = todoRepository.findTodoDto();
        for (TodoDto dto : todoDto) {
            System.out.println("dto = " + dto);
        }
    }

}