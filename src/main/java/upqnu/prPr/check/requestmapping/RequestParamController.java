package upqnu.prPr.check.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
//        int age = Integer.parseInt(request.getParameter("age"));
        log.info("email={}, password={}", email, password);

        response.getWriter().write("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 파라미터 조회 테스트입니다.");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParam2(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        log.info("email={}, password={}", email, password);

        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 파라미터 조회 테스트2입니다.";
        // @Controller + String 이면 viewresolver를 통해 "~"라는 이름의 view를 찾게 된다. 이 때 @ResponseBody를 붙여주면 "~"을 http 응답메시지로 내보낸다.
        // @ResponseBody 대신 @Controller -> @RestController로 변경해도 된다.
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParam3(
            @RequestParam String email,
            @RequestParam String password) { // @RequestParam("email") String email처럼 email이 중복될 때 ("email") 생략가능

        log.info("email={}, password={}", email, password);

        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 파라미터 조회 테스트3입니다.";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParam4(String email, String password) {
        log.info("email={}, password={}", email, password);

        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 파라미터 조회 테스트4입니다.";
    }

}
