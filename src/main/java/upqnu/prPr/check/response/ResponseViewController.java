package upqnu.prPr.check.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseView1() {
        ModelAndView mav = new ModelAndView("response/hello-test")
                .addObject("data", "OK. 개인프로젝트 관리 서비스 prPr를 위한 view template 테스트입니다.");

        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseView2(Model model) {
        model.addAttribute("data", "OK. 개인프로젝트 관리 서비스 prPr를 위한 view template 테스트2입니다.");

        return "response/hello-test"; // @Controller + String으로 반환할 경우 = return값은 view 논리적 이름이 된다.
    }

    @ResponseBody
    @RequestMapping("/response-view-v2-1")
    public String responseView2_1(Model model) {
        model.addAttribute("data", "OK. 개인프로젝트 관리 서비스 prPr를 위한 view template 테스트2입니다.");

        return "response/hello-test"; // v2와 차이는 @ResponseBody를 붙인 것 뿐이지만, return값이 view 논리적 이름이 되는 것이 아니라 그대로 출력된다.
    }

    @RequestMapping("/response/hello-test")
    public void responseView3(Model model) {
        model.addAttribute("data", "OK. 개인프로젝트 관리 서비스 prPr를 위한 view template 테스트3입니다.");
    }

}
