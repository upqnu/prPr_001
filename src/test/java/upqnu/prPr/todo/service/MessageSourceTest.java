package upqnu.prPr.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() { // 메시지가 없는 경우에는 NoSuchMessageException이 발생한다.
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class); // messages.properties에 "no_code"가 없어서 NoSuchMessageException 발생해야 하지만, 이 경우 이 예외에 해당된다고 설정했으므로 테스트 통과된다
    }

    @Test
    void notFoundMessageCodeDefaultMessage() { // 메시지가 없어도 기본 메시지( defaultMessage )를 사용하면 기본 메시지가 반환되도록 설정
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() { // messages.properties에서 hello.name=안녕 {0}을 불러옴. {0}대신 배열로 {"Spring"} 입력되면 -> "안녕 Spring"으로 출력
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() { // 국제화 테스트
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }


    @Test
    void enLang() { // 국제화 테스트
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }

}
