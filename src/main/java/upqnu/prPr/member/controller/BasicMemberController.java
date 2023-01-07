package upqnu.prPr.member.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import upqnu.prPr.member.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicMemberController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "안녕하세요. 개인 프로젝트 관리 서비스, ~입니다!");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "안녕하세요. <b>개인 프로젝트 관리 서비스, ~입니다!</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        Member memberA = new Member("memberA", "aaa@gmail.com");
        Member memberB = new Member("memberB", "bbb@gmail.com");
        List<Member> list = new ArrayList<>();
        list.add(memberA);
        list.add(memberB);
        Map<String, Member> map = new HashMap<>();
        map.put("memberA", memberA);
        map.put("memberB", memberB);
        model.addAttribute("member", memberA);
        model.addAttribute("members", list);
        model.addAttribute("memberMap", map);
        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");
        return "basic/literal";
    }

    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute() {
        return "basic/attribute";
    }

    @GetMapping("/each")
    public String each(Model model) {
        addMembers(model);
        return "basic/each";
    }

    @GetMapping("/condition")
    public String condition(Model model) {
        addMembers(model);
        return "basic/condition";
    }

    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data", "Spring!");
        return "basic/comments";
    }

    @GetMapping("/block")
    public String block(Model model) {
        addMembers(model);
        return "basic/block";
    }

    @GetMapping("/javascript")
    public String javascript(Model model) {
        model.addAttribute("member", new Member("memberA", "aaa@gmail.com"));
        addMembers(model);
        return "basic/javascript";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

    private void addMembers(Model model) {
        List<Member> list = new ArrayList<>();
        list.add(new Member("memberA", "aaa@gmail.com"));
        list.add(new Member("memberB", "bbb@gmail.com"));
        list.add(new Member("memberC", "ccc@gmail.com"));
        model.addAttribute("members", list);
    }
}
