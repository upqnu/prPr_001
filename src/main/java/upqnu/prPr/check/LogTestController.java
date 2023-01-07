package upqnu.prPr.check;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); // 현재의 클래스 - getClass() 대신 LogTestController를 입력해도 된다.

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "prPr";

        System.out.println("name = " + name);
        
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 logger 테스트입니다.";
    }
}
