package upqnu.prPr.check.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import upqnu.prPr.member.entity.Member;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Controller
//@ResponseBody
@RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBody1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK. 개인프로젝트 관리 서비스 prPr를 위한 Http API - 메시지 바디에 직접입력 테스트입니다.");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBody2() throws IOException {
        return new ResponseEntity<>("OK. 개인프로젝트 관리 서비스 prPr를 위한 Http API - 메시지 바디에 직접입력 테스트2입니다.", HttpStatus.OK);
    }

//    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBody3() {
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 Http API - 메시지 바디에 직접입력 테스트3입니다.";
    }

    @GetMapping("response-body-json-v1")
    public ResponseEntity<Member> responseBodyJsonV1() {
        Member member = new Member();
        member.setEmail("ccc@gmail.com");
        member.setPassword("eee");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    //ResponseEntity 는 HTTP 응답 코드를 설정할 수 있는데, @ResponseBody 를 사용하면 이런 것을 설정하기 까다롭다.
    // @ResponseStatus(HttpStatus.OK) 애노테이션을 사용하면 응답 코드도 설정할 수 있다.
    // 물론 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다. 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity 를 사용하면 된다.
    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
    @GetMapping("response-body-json-v2")
    public Member responseBodyJsonV2() {
        Member member = new Member();
        member.setEmail("ddd@gmail.com");
        member.setPassword("jjj");
        return member;
    }
}
