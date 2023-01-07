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

//    @PostMapping("/add")
    public String addTodo1(@RequestParam String itemName, // addTodo.html의 input 태그에서의 name과 일치해야
                       @RequestParam String price, // addTodo.html의 input 태그에서의 name과 일치해야
                       Model model) { // 같은 URI이지만 Post와 Get 구현 - Post와 Get의 메서드가 구분되어 있다.
        Todo todo = new Todo();
        todo.setTodoTitle(itemName);
        todo.setTodoBody(price);

        todoMvcRepository.save(todo);

        model.addAttribute("todo", todo);

        return "basic/todo";
    }

//    @PostMapping("/add")
    public String addTodo2(@ModelAttribute("todo") Todo todo, Model model) {

        todoMvcRepository.save(todo);

//        model.addAttribute("todo", todo); // 자동추가됨, 생략 가능

        return "basic/todo";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(todo); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item */
//    @PostMapping("/add")
    public String addTodo3(@ModelAttribute Todo todo) {

        todoMvcRepository.save(todo);

        return "basic/todo";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(todo) 자동 추가 */
//    @PostMapping("/add")
    public String addTodoV4(Todo todo) {
        todoMvcRepository.save(todo);
        return "basic/todo";
    }

//    @PostMapping("/add")
    public String addTodoV5(Todo todo) {
        todoMvcRepository.save(todo);
        return "redirect:/basic/todos/" + todo.getTodoId();
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
