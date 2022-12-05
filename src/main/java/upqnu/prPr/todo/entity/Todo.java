package upqnu.prPr.todo.entity;

import lombok.*;
import upqnu.prPr.audit.BaseEntity;
import upqnu.prPr.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"todoId", "todoTitle", "todoBody"})
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    private Long todoId;

//    private TodoStatus todoStatus;

    private String todoTitle;
    private String todoBody;
//    private ??? todoTime;
//    private TodoComp todoComp; // 완료여부 - todo가 존재하는 상태에서 완료/미완료로 구분

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
        if(member != null) chnageMember(member);
    }

    public void chnageMember(Member member) {
        this.member = member;
        member.getToDos().add(this);
    }

}
