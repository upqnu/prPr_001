package upqnu.prPr.check.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

//    private Logger log = LoggerFactory.getLogger(getClass()); // @Slf4j 어노테이션이 대체할 수 있다.

//    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    @GetMapping(value = "/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 mapping 테스트입니다.";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mapping-get-v2");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 mapping 테스트 V1입니다.";
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 mapping 테스트 V2입니다.";
    }

    @GetMapping("/mapping/{todoId}")
//    public String mappingPath(@PathVariable("todoId") Long todoId) {
    public String mappingPath(@PathVariable Long todoId) { // @PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.

            log.info("mappingPath todoId={}", todoId);
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 mapping - todoId를 이용한 경로변수(@PathVariable) 테스트입니다.";
    }

    @GetMapping("/mapping/members/{name}/todos/{todoTitle}")
    public String mappingPath(@PathVariable String name, @PathVariable String todoTitle) {
        log.info("mappingPath name={}, todoTitle={}", name, todoTitle);
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 중복mapping - 유저name, todoTitle를 이용한 경로변수(@PathVariable) 테스트입니다.";
    }

    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 특정 파라미터 조건 mapping 테스트입니다.";
    }

    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 특정 헤더 조건 mapping 테스트입니다.";
    }

//    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 미디어 타입 조건 mapping - Content-Type, consume 테스트입니다.";
    }

//    @PostMapping(value = "/mapping-produce", produces = "text/html")
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "OK. 개인프로젝트 관리 서비스 prPr를 위한 미디어 타입 조건 mapping - HTTP 요청 Accept, produce 테스트입니다.";
    }
}
