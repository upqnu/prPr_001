package upqnu.prPr.member.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;

//    private MemberStatus memberStatus;

    private String name;

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }
}
