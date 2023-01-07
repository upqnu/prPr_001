package upqnu.prPr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 스프링 부트 thymeleaf viewName 매핑
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { // Model을 통해 Controller에서 View로 데이터를 넘길 수 있음
        model.addAttribute("data","hello!!!");
        return "hello"; // 화면이름 출력 - resources > templates > hello.html
    }
}
