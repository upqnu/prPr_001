package upqnu.prPr.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import upqnu.prPr.member.repository.MemberRepository;
import upqnu.prPr.todo.dto.TodoDto;
import upqnu.prPr.todo.entity.Todo;
import upqnu.prPr.todo.repository.TodoRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    @GetMapping("/todos")
    public Page<TodoDto> list(Pageable pageable) {
//        Page<Todo> page = todoRepository.findAll(pageable);
//        Page<TodoDto> map = page.map(todo -> new TodoDto(todo.getTodoId(), todo.getTodoTitle(), null));
//        return map;

//        // command + option + N 으로 inline variable로 변경
//        return todoRepository.findAll(pageable)
//                .map(todo -> new TodoDto(todo.getTodoId(), todo.getTodoTitle(), null));

        return todoRepository.findAll(pageable)
                .map(TodoDto::new);
    }

    @PostConstruct
    public void init() {
        for(int i = 0; i < 100; i++) {
            todoRepository.save(new Todo("title" + i));
        }
    }
}
