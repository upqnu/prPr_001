package upqnu.prPr.todo.service;

import org.junit.jupiter.api.Test;
import upqnu.prPr.todo.entity.Todo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation() {
        // 다음 코드와 같이 검증기를 생성한다. 이후 스프링과 통합하면 우리가 직접 이런 코드를 작성하지는 않으므로, 이렇게 사용하는구나 정도만 참고하자.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Todo todo = new Todo();
        todo.setTodoTitle(" "); //공백
        todo.setTodoBody("");

        // 검증 실행
        // 검증 대상( item )을 직접 검증기에 넣고 그 결과를 받는다. Set 에는 ConstraintViolation 이라는 검증 오류가 담긴다. 따라서 결과가 비어있으면 검증 오류가 없는 것이다.
        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);
        for (ConstraintViolation<Todo> violation : violations) {
            System.out.println("violation=" + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }
    }
}

// 스프링은 이미 개발자를 위해 빈 검증기를 스프링에 완전히 통합해두었다.