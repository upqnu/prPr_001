package upqnu.prPr.todo.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class TodoDto {

    private Long todoId;
    private String todoTitle;
    private String name;

    public TodoDto(Long todoId, String todoTitle, String name) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.name = name;
    }
}
