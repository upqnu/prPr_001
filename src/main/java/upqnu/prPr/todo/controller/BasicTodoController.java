package upqnu.prPr.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import upqnu.prPr.member.entity.Member;
import upqnu.prPr.todo.entity.Todo;
import upqnu.prPr.todo.repository.TodoMvcRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/todos")
@RequiredArgsConstructor
public class BasicTodoController {

    private final TodoMvcRepository todoMvcRepository;

    @GetMapping
    public String todos(Model model) {
        List<Todo> todos = todoMvcRepository.findAll();
        model.addAttribute("todos", todos);
        return "basic/todos";
    }

    @GetMapping("/{todoId}")
    public String todo(@PathVariable long todoId, Model model) {
        Todo todo = todoMvcRepository.findById(todoId);
        model.addAttribute("todo", todo);
        return "basic/todo";
    }

    @GetMapping("/add")
    public String addTodo(Model model) {
        model.addAttribute("todo", new Todo());
        return "basic/addTodo";
    }

    /**
     * PRG (Post/Redirect/Get) 및 RedirectAttributes 적용
     */
    @PostMapping("/add")
    public String addTodoV6(Todo todo, RedirectAttributes redirectAttributes) {
        log.info("todo.done={}", todo.getDone());

        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    @GetMapping("/{todoId}/edit")
    public String editTodo(@PathVariable Long todoId, Model model) {
        Todo todo = todoMvcRepository.findById(todoId);
        model.addAttribute("todo", todo);
        return "basic/editTodo";
    }

    @PostMapping("/{todoId}/edit")
    public String edit(@PathVariable Long todoId, @ModelAttribute Todo todo) {
        log.info("todo.done={}", todo.getDone());

        todoMvcRepository.update(todoId, todo);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        todoMvcRepository.save(new Todo("title1", new Member("member1", "aaa@gmail.com")));
        todoMvcRepository.save(new Todo("title2", new Member("member2", "bbb@gmail.com")));
    }
}
