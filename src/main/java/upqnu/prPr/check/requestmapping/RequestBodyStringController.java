package upqnu.prPr.check.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.WriteAbortedException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // String은 바이트코드. 바이트코드로 문자를 받을 때 어떻게 인코딩할 것인지 정해줘야 하며, 그렇지 않으면 OS 기본설정, Java 기본설정 등 디폴트로 인코딩.

        log.info("messageBody={}", messageBody);

        response.getWriter().write("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트입니다.");
    }

    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
//        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

//        response.getWriter().write("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트2입니다.");
        responseWriter.write("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트2입니다.");
    }

    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException { // HttpEntity<String> ; 스프링이 http body에 있는 데이터를 문자열로 바꿔서
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트3입니다.");
    }

    @PostMapping("/request-body-string-v3-1")
    public HttpEntity<String> requestBodyStringV3_1(RequestEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new ResponseEntity<String >("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트3-1입니다.", HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
//    public HttpEntity<String> requestBodyStringV4(@RequestBody String messageBody) {
    public String requestBodyStringV4(@RequestBody String messageBody) {

//        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

//        return new ResponseEntity<String >("OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트4입니다.", HttpStatus.CREATED);
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 요청 메시지 - 단순 텍스트 테스트4입니다.";
    }
}
