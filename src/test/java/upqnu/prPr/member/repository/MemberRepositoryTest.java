package upqnu.prPr.member.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import upqnu.prPr.member.entity.Member;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false) // 롤백 안 하고 commit 한다는 의미.
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

//        Optional<Member> byId = memberRepository.findById(savedMember.getMemberId());

        Member findMember = memberRepository.findById(savedMember.getMemberId()).get();// id가 null이면 예외 발생하므로 원래 orElse와 함께 써야 한다.

        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);
    }

}