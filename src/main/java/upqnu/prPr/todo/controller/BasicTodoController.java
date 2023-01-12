package upqnu.prPr.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
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
//    private final TodoValidator todoValidator;
//
//    @InitBinder
//    public void init(WebDataBinder dataBinder) {
//        log.info("init binder {}", dataBinder);
//        dataBinder.addValidators(todoValidator);
//    }

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

    /**
     * @ModelAttribute 은 자동으로
     * (1) 파라미터로 넘겨 준 타입(todo)의 객체를 자동 생성
     * (2) 생성된 객체 HTTP로 넘어 온 값들을 자동으로 쿼리스트링 형태로 바인딩
     * (3) (todo) 객체가 자동으로 Model객체에 추가되고 View로 전달
     */
//    @PostMapping("/add")
    public String addTodo2(@ModelAttribute("todo") Todo todo, Model model) {

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
//    @PostMapping("/add")
    public String addTodoV6(Todo todo, RedirectAttributes redirectAttributes) {

        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}"; // pathVariable 바인딩: {todoId} / 나머지는 쿼리 파라미터로 처리: ?status=true
    }

//    @PostMapping("/add")
    public String addTodoV7(@ModelAttribute Todo todo, RedirectAttributes redirectAttributes, Model model) {

        // 검증 오류 결과를 errors에 보관
        Map<String , String> errors = new HashMap<>();

        //검증 로직
//        if (!StringUtils.hasText(todo.getTodoTitle())) {
//            errors.put("todoTitle", "할일 제목은 필수입니다.");
//        }

        if (todo.getTodoTitle() == null || todo.getTodoTitle().length() > 50 || todo.getTodoTitle().length() < 3) {
            errors.put("todoTitle", "할일 제목은 3자 이상, 50자 이하로 작성할 수 있습니다.");
        }

        if (todo.getTodoBody() == null || todo.getTodoBody().length() > 300 || todo.getTodoBody().length() < 5) {
            errors.put("todoBody", "할일 내용은 5자 이상, 300자 이하로 작성할 수 있습니다.");
        }

//        //특정 필드가 아닌 복합 룰 검증 (이 프로젝트에서는 그런 부분이 특별히 없다)
//        if (todo.getPrice() != null && todo.getQuantity() != null) {
//            int resultPrice = todo.getPrice() * todo.getQuantity();
//            if (resultPrice < 10000) {
//                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
//            }
//        }

        //검증에 실패하면 다시 입력 폼으로
        if (!errors.isEmpty()) {
            log.info("errors = {} ", errors);
            model.addAttribute("errors", errors);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * BindingResult ; @BindingResult를 @ModelAttribute 뒤에 넣어주면 검증 값을 담아줄 수 있다.
     * addError() ;  ObjectError or FieldError 를 에러리스트에 담아 줌
     * FieldError 는 오류 발생시 사용자 입력 값을 저장하는 기능을 제공한다.
     */
//    @PostMapping("/add")
    public String addTodoV8(@ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

//        if (!StringUtils.hasText(todo.getTodoTitle())) {
//            bindingResult.addError(new FieldError("todo", "todoTitle",
//                    todo.getTodoTitle(), false, null, null, "할일 제목은 필수입니다.")); }


        if (todo.getTodoTitle() == null || todo.getTodoTitle().length() > 50 || todo.getTodoTitle().length() < 3) {
            bindingResult.addError(new FieldError("todo", "todoTitle",
                    todo.getTodoTitle(), // 사용자가 잘못된 값을 입력하면 -> FieldError가 그 값을 rejectedValue에 넣어주기 때문에 -> 입력칸에 그대로 남아있게 된다
                    false, null, null,"할일 제목은 3자 이상, 50자 이하로 작성할 수 있습니다."));
        }

//        if (todo.getTodoBody() == null || todo.getTodoBody().length() > 300 || todo.getTodoBody().length() < 5) {
//            bindingResult.addError(new FieldError("todo", "todoBody", todo.getTodoBody(), false, null, null,
//                    "할일 내용은 5자 이상, 300자 이하로 작성할 수 있습니다."));
//        }

//        //특정 필드 예외가 아닌 전체 예외 (이 프로젝트에서는 그런 부분이 특별히 없다) - 전역 오류 ObjectError 이용
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
//            }
//        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * errors.properties 추가
     * addError()의 파라미터 중 default메시지를 일일이 입력하기가 번거롭다.
     * errors.properties 파일을 생성하여 에러 메시지를 별도로 관리 -> addError()의 파라미터 중 code를 String 배열 형태 + errors.properties에 관리된 에러 메시지 중 알맞는 것을 지정.
     * 메시지 코드는 하나가 아니라 배열로 여러 값을 전달할 수 있는데, 순서대로 매칭해서 처음 매칭되는 메시지가 사용된다.
     */
//    @PostMapping("/add")
    public String addTodoV9(@ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

//        if (!StringUtils.hasText(todo.getTodoTitle())) {
//            bindingResult.addError(new FieldError("todo", "todoTitle",
//                    todo.getTodoTitle(), false, new String[]{"required.todo.todoTitle"}, null, null));
//        }

        if (todo.getTodoTitle() == null || todo.getTodoTitle().length() > 50 || todo.getTodoTitle().length() < 3) {
            bindingResult.addError(new FieldError("todo", "todoTitle",
                    todo.getTodoTitle(), false, new String[]{"range.todo.todoTitle"}, null, null));
        }

//        //특정 필드 예외가 아닌 전체 예외 (이 프로젝트에서는 그런 부분이 특별히 없다) - 전역 오류 ObjectError 이용
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", new String[]
//                        {"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
//            }
//        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * BindingResult 가 제공하는 rejectValue() , reject() 를 사용하면 FieldError , ObjectError 를 직접 생성하지 않고, 깔끔하게 검증 오류를 다룰 수 있다.
     */
//    @PostMapping("/add")
    public String addTodoV10(@ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

//        if (!StringUtils.hasText(todo.getTodoTitle())) {
//            bindingResult.rejectValue("todoTitle", "required");
//        }

//        // 위 코드를 ValidationUtils 사용하여 더 단순화할 수 있다
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "todoTitle","required");


        if (todo.getTodoTitle() == null || todo.getTodoTitle().length() > 50 || todo.getTodoTitle().length() < 3) {
            bindingResult.rejectValue("todoTitle","range", new String[]{"range.todo.todoTitle"}, null);
        }

//        //특정 필드 예외가 아닌 전체 예외 (이 프로젝트에서는 그런 부분이 특별히 없다)
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null));
//            }
//        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * (1) 에러 코드에 우선 순위를 부여 - errors.properties에 우선 순위를 레벨 1~4로 두었다.
     * (2) Validator로 분리1 ; Validator 인터페이스를 상속한 TodoValidator 클래스를 생성하여 validate 로직을 옮겨 둔다. (controller에서 validate을 분리해낸 것)
     */
//    @PostMapping("/add")
    public String addTodoV11(@ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

//        todoValidator.validate(todo, bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * Validator로 분리2 ; WebDataBinder(스프링의 파라미터 바인딩의 역할을 해주고 검증 기능도 내부에 포함) 객체를 매개변수로 하는 메서드 void init으로 검증기 자동 적용 (해당 controller에만 영향 줌)
     * 위 Validator 1에서 validator를 직접 호출하는 부분이 사라지고, 대신에 검증 대상 앞에 @Validated 가 붙었다.
     */
//    @PostMapping("/add")
    public String addTodoV12(@Validated @ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    /**
     * 스프링 부트가 spring-boot-starter-validation 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합한다. -> @Valid, @Validated 만 적용하면 된다.
     * @ModelAttribute 각각의 필드 타입 변환시도 변환에 성공한 필드만 BeanValidation 적용
     */
    @PostMapping("/add")
    public String addTodoV13(@Validated @ModelAttribute Todo todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {} ", bindingResult);
            return "basic/addTodo";
        }

        //성공 로직
        Todo savedTodo = todoMvcRepository.save(todo);
        redirectAttributes.addAttribute("todoId", savedTodo.getTodoId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/todos/{todoId}";
    }

    @GetMapping("/{todoId}/edit") // todo 수정 form 보여주기
    public String editTodo(@PathVariable Long todoId, Model model) {
        Todo todo = todoMvcRepository.findById(todoId);
        model.addAttribute("todo", todo);
        return "basic/editTodo";
    }

    @PostMapping("/{todoId}/edit") // todo 수정하기
    public String edit(@PathVariable Long todoId, @ModelAttribute Todo todo) {

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
