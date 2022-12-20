package upqnu.prPr.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import upqnu.prPr.member.entity.Member;
import upqnu.prPr.todo.entity.Todo;
import upqnu.prPr.todo.repository.TodoRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/todos")
@RequiredArgsConstructor
public class BasicTodoController {

    private final TodoRepository todoRepository;

    @GetMapping
    public String todos(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "basic/todos";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        todoRepository.save(new Todo("title1", new Member("member1", "aaa@gmail.com")));
        todoRepository.save(new Todo("title2", new Member("member2", "bbb@gmail.com")));
    }
}
