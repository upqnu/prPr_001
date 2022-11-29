package upqnu.prPr.todo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import upqnu.prPr.member.entity.Member;
import upqnu.prPr.member.repository.MemberRepository;
import upqnu.prPr.todo.dto.TodoDto;
import upqnu.prPr.todo.entity.Todo;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void findTodo() {
        Todo todo1 = new Todo("title1", "body1");
        Todo todo2 = new Todo("title2", "body2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        //단건 조회 검증
        List<Todo> result = todoRepository.findTodo("title1", "body1");
        assertThat(result.get(0).getTodoTitle()).isEqualTo("title1");
        assertThat(result.get(0).getTodoBody()).isEqualTo("body1");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findTodoTitleList() {
        Todo todo1 = new Todo("title1", "body1");
        Todo todo2 = new Todo("title2", "body2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        //리스트 조회 검증
        List<Todo> all = todoRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
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

    @Test
    public void findByTitles() {
        Todo todo1 = new Todo("title1", "body1");
        Todo todo2 = new Todo("title2", "body2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        List<Todo> todoTitleList = todoRepository.findByTitles(Arrays.asList("title1", "title2"));
        for(Todo todo : todoTitleList) {
            System.out.println("todo = " + todo);
        }
    }

    @Test
    public void returnType() {
        Todo todo1 = new Todo("title1", "body1");
        Todo todo2 = new Todo("title2", "body2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        // 컬렉션
        List<Todo> title1 = todoRepository.findListByTodoTitle("title1"); // title1,2가 아닌 todoTitle을 매개변수로 넣으면 -> 로그에는 title1 = [] 로 찍힌다.
        System.out.println("title1 = " + title1);

        // 단건
        Todo title2 = todoRepository.findTodoByTodoTitle("title3"); // title1,2가 아닌 todoTitle을 매개변수로 넣으면 -> 로그에는 title2 = null 로 찍힌다.
        System.out.println("title2 = " + title2); // 그렇다면 javax.persistence.NoResultException 예외가 발생해야 하지만 -> 스프링 데이터 JPA는 단건을 조회할 때 이 예외가 발생하면 예외를 무시하고 대신에 null 을 반환한다.

        // 단건 Optional
        Optional<Todo> title3 = todoRepository.findOptionalByTodoTitle("title3"); // title1,2가 아닌 todoTitle을 매개변수로 넣으면 -> 로그에는 title3 = Optional.empty 로 찍힌다.
        System.out.println("title3 = " + title3); // 단건 Optional을 사용하게 되면 그냥 단건을 할 필요가 없어진다. orElse, orThrow 등으로 구성할 수도 있다.
    }

    @Test
    public void paging() {
        todoRepository.save(new Todo("title1","body1"));
        todoRepository.save(new Todo("title2","body1"));
        todoRepository.save(new Todo("title3","body1"));
        todoRepository.save(new Todo("title4","body1"));
        todoRepository.save(new Todo("title5","body2"));

//        String todoBody = "body1";

        PageRequest pageRequest =
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "todoTitle")); // Spring Data JPA는 페이지를 1이 아닌 0부터 시작함. sorting 필요없으면 빼도 된다.

        Page<Todo> page = todoRepository.findByTodoBody("body2", pageRequest); // Pageable이 있어서 구현체 없이도 구현 가능 + totalCount 쿼리도 날려 준다.

        List<Todo> content = page.getContent();
//        long totalElements = page.getTotalElements();
//
//        for(Todo todo : content) {
//            System.out.println("todo = " + todo);
//        }
//
//        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(1); // 페이지사이즈 = 3 이지만, "body2"는 1개 뿐이기 때문에 1로 기대 ("body1"이었다면 총 4개 있지만 페이지사이즈 = 3 이니까 3으로 기대)
        assertThat(page.getTotalElements()).isEqualTo(1); // 총 컨텐츠 갯수는 5개이지만 body2"는 1개 뿐이기 때문에 1로 기대
        assertThat(page.getNumber()).isEqualTo(0); // page number가져오기. 첫 페이지여서 0
        assertThat(page.getTotalPages()).isEqualTo(1); // 총 페이지 갯수 ("body1"이었다면 총 4개의 컨텐트, 페이지사이즈 = 3 이니까 2로 기대)
        assertThat(page.isFirst()).isTrue(); // 첫번째 페이지 존재여부
        assertThat(page.hasNext()).isFalse(); // 다음 페이지 존재여부 ("body1"이었다면 총 4개의 컨텐트, 페이지사이즈 = 3 이니까 총 2페이지이므로 isTrue)
    }

}