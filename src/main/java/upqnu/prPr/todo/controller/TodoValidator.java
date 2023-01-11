package upqnu.prPr.todo.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import upqnu.prPr.todo.entity.Todo;

@Component
public class TodoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Todo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Todo todo = (Todo) target;

//        if (!StringUtils.hasText(todo.getTodoTitle())) {
//            errors.rejectValue("todoTitle", "required");
//        }

//        // 위 코드를 ValidationUtils 사용하여 더 단순화할 수 있다
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "todoTitle","required");


        if (todo.getTodoTitle() == null || todo.getTodoTitle().length() > 50 || todo.getTodoTitle().length() < 3) {
            errors.rejectValue("todoTitle","range", new String[]{"range.todo.todoTitle"}, null);
        }

//        //특정 필드 예외가 아닌 전체 예외 (이 프로젝트에서는 그런 부분이 특별히 없다)
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null));
//            }
//        }

    }
}
