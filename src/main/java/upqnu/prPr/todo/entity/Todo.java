package upqnu.prPr.todo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import upqnu.prPr.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"todoId", "title", "todoBody"})
public class Todo {

    @Id
    @GeneratedValue
    private Long todoId;

//    private TodoStatus todoStatus;

    private String title;
    private String todoBody;
//    private ??? todoTime;
//    private TodoComp todoComp; // 완료여부 - todo가 존재하는 상태에서 완료/미완료로 구분

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    public Todo(String title, Member member) {
        this.title = title;
        if(member != null) chnageMember(member);
    }

    public void chnageMember(Member member) {
        this.member = member;
        member.getToDos().add(this);
    }

}
