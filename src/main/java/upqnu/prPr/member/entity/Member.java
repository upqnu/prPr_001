package upqnu.prPr.member.entity;

import lombok.*;
import upqnu.prPr.audit.BaseEntity;
import upqnu.prPr.todo.entity.Todo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC) // JPA는 기본 생성자 있어야 하나, 표시하고 싶지 않을 때 @NoArgsConstructor 사용. 단, JPA스펙 상(생성자에 private을 허용하지 않으므로) PROTECTED로 열어두는 것.
//@ToString(of = {"memberId","name","email","password"}) // 명시된 필드값을 출력한다. Todo와 연관관계가 있어 순환참조 발생할 가능성이 있다.(@Data사용시에도 같은 위험이 있음)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long memberId;

//    private MemberStatus memberStatus;
//    private UserRole userRole;

    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Todo> toDos = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
