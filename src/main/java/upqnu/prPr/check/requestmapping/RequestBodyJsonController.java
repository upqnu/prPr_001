package upqnu.prPr.check.requestmapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import upqnu.prPr.member.entity.Member;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        Member member = objectMapper.readValue(messageBody, Member.class);
        log.info("email={}, password={}", member.getEmail(), member.getPassword());

        response.getWriter().write("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청메시지 - JSON 테스트입니다.");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
//        ServletInputStream inputStream = request.getInputStream();
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        Member member = objectMapper.readValue(messageBody, Member.class);
        log.info("email={}, password={}", member.getEmail(), member.getPassword());

        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청메시지 - JSON 테스트2입니다.";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody Member member) {
//        log.info("messageBody={}", messageBody);
//        Member member = objectMapper.readValue(messageBody, Member.class);
        log.info("email={}, password={}", member.getEmail(), member.getPassword());
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청메시지 - JSON 테스트3입니다.";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<Member> member) {
        Member jsonMember = member.getBody();
        log.info("email={}, password={}", jsonMember.getEmail(), jsonMember.getPassword());
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청메시지 - JSON 테스트4입니다.";
    }

    /**
    * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
    * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content- type: application/json)
    *
    * @ResponseBody 적용
    * - 메시지 바디 정보 직접 반환(view 조회X)
    * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept: application/json)
    */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public Member requestBodyJsonV5(@RequestBody Member member) {
        log.info("email={}, password={}", member.getEmail(), member.getPassword());
        return member;
    }
}
