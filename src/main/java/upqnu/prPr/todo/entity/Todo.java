package upqnu.prPr.todo.entity;

import lombok.*;
import upqnu.prPr.audit.BaseEntity;
import upqnu.prPr.member.entity.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString(of = {"todoId", "todoTitle", "todoBody"})
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    private Long todoId;

//    private TodoStatus todoStatus;

    @NotBlank
    @Size(min = 3, max = 50)
    private String todoTitle;

    @Size(max = 200)
    private String todoBody;

//    private ??? todoTime;

    @Enumerated(EnumType.STRING)
    private TodoCmplt todoCmplt = TodoCmplt.TODO_INCOMPLETE; // 완료여부 - todo가 존재하는 상태에서 완료/미완료로 구분

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    public Todo(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public Todo(String todoTitle, String todoBody) {
        this.todoTitle = todoTitle;
        this.todoBody = todoBody;
    }

    public Todo(String todoTitle, Member member) {
        this.todoTitle = todoTitle;
        if(member != null) changeMember(member);
    }

    public void changeMember(Member member) {
        this.member = member;
        member.getToDos().add(this);
    }

    public enum TodoCmplt {
        TODO_COMPLETE("완료"), TODO_INCOMPLETE("미완료");

        private final String cmplt;

        TodoCmplt(String cmplt) {
            this.cmplt = cmplt;
        }
    }

}
