package upqnu.prPr.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import upqnu.prPr.member.entity.Member;
import upqnu.prPr.todo.entity.Todo;
import upqnu.prPr.todo.repository.TodoMvcRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/basic/todos")
@RequiredArgsConstructor
public class BasicTodoController {

    private final TodoMvcRepository todoMvcRepository;

    @GetMapping
    public String todos(Model model) { // Model 객체는 컨트롤러에서 데이터를 생성해 이를 JSP 즉 View에 전달하는 역할. HashMap 형태 - key, value값을 저장
        List<Todo> todos = todoMvcRepository.findAll();
        model.addAttribute("todos", todos); // addAttribute메서드를 통해 View로 데이터를 전달
        return "basic/todos"; // 반환URI
    }

    @GetMapping("/{todoId}")
    public String todo(@PathVariable long todoId, Model model) { // 스프링부트에서 URL로 파라미터를 전달하는 두가지 방식 중 - 가져올 파라미터를 {}형태로 작성해줘야 파라미터로 매핑이 이뤄진다.
        Todo todo = todoMvcRepository.findById(todoId);
        model.addAttribute("todo", todo);
        return "basic/todo";
    }

    @GetMapping("/add") // 할일 등록 form으로 이동
    public String addTodo(Model model) {
        model.addAttribute("todo", new Todo());
        return "basic/addTodo";
    }

//    @PostMapping("/add") // 쿼리스트링을 통해 요청 파라미터를 전송하므로 @RequestParam 사용
    public String addTodo1(@RequestParam String todoTitle, // addTodo.html의 input 태그에서의 name속성의 값과 일치해야 한다
                       @RequestParam String todoBody, // addTodo.html의 input 태그에서의 name속성의 값과 일치해야 한다
                       Model model) { // 같은 URI이지만 Post와 Get 구현 - Post와 Get의 메서드가 구분되어 있다.
        Todo todo = new Todo();
        todo.setTodoTitle(todoTitle);
        todo.setTodoBody(todoBody);

        todoMvcRepository.save(todo);

        model.addAttribute("todo", todo);

        return "basic/todo";
    }

//    @PostMapping("/add")
    public String addTodo2(@ModelAttribute("todo") Todo todo, Model model) { // @ModelAttribute 은 자동으로 (1) 파라미터로 넘겨 준 타입(todo)의 객체를 자동 생성 (2) 생성된 객체 HTTP로 넘어 온 값들을 자동으로 쿼리스트링 형태로 바인딩 (3) (todo) 객체가 자동으로 Model객체에 추가되고 View로 전달

        todoMvcRepository.save(todo); // Todo의 객체를 자동생성한 후 저장

//        model.addAttribute("todo", todo); // 자동추가됨, 생략 가능

        return "basic/todo";
    }

    /**
     * @ModelAttribute 에서 name 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Todo -> todo
     * model.addAttribute(todo); 자동 추가, 생략 가능
     */
//    @PostMapping("/add")
    public String addTodo3(@ModelAttribute Todo todo) {

        todoMvcRepository.save(todo);

        return "basic/todo";
    }

    /**
     * @ModelAttribute 자체도 생략 가능
     * model.addAttribute(todo) 자동 추가 */
//    @PostMapping("/add")
    public String addTodoV4(Todo todo) {
        todoMvcRepository.save(todo);
        return "basic/todo";
    }

    /**
     * PRG (Post/Redirect/Get) 적용
     * 웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송. todo등록 시, post로 데이터를 서버에 전송한 후
     * 새로고침을 하면 마지막에 전송한 post가 다시 실행. 그러면 내용은 같고 id만 다른 todo데이터가 계속 쌓임.
     * 이를 해결하려면 todo등록 후 자동으로 리다이렉트하여 todo상세페이지로 이동(get)하게 하면 된다.
     * (이 경우, 새로고침을 해도 get이 호출된다)
     */
//    @PostMapping("/add")
    public String addTodoV5(Todo todo) { // PRG구현
        todoMvcRepository.save(todo);
        return "redirect:/basic/todos/" + todo.getTodoId();
        // todo등록 후 자동으로 리다이렉트하여 todo상세페이지로 이동(get) -> 새로고침 시 불필요한 todo데이터가 쌓이는 것을 방지.
    }

    /**
     * PRG (Post/Redirect/Get) 및 RedirectAttributes 적용
     * RedirectAttributes 클래스를 사용하여 redirect 시 데이타를 전달할 수 있다.
     * RedirectAttributes 를 사용하면 URL 인코딩도 해주고, pathVarible , 쿼리 파라미터까지 처리해준다.
     */
    @PostMapping("/add")
    public String addTodoV6(Todo todo, RedirectAttributes redirectAttributes) {
        log.info("todo.done={}", todo.getDone());

        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}"; // pathVariable 바인딩: {todoId} / 나머지는 쿼리 파라미터로 처리: ?status=true
    }

    @GetMapping("/{todoId}/edit") // todo 수정 form 보여주기
    public String editTodo(@PathVariable Long todoId, Model model) {
        Todo todo = todoMvcRepository.findById(todoId);
        model.addAttribute("todo", todo);
        return "basic/editTodo";
    }

    @PostMapping("/{todoId}/edit") // todo 수정하기
    public String edit(@PathVariable Long todoId, @ModelAttribute Todo todo) {
        log.info("todo.done={}", todo.getDone());

        todoMvcRepository.update(todoId, todo);
        return "redirect:/basic/todos/{todoId}"; // todo 수정 후, todo 상세페이지로 이동 - 이동하는데 redirect 사용
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
