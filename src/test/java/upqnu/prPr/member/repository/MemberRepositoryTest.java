package upqnu.prPr.member.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import upqnu.prPr.member.entity.Member;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false) // 롤백 안 하고 commit 한다는 의미.
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

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getMemberId()).get();
        Member findMember2 = memberRepository.findById(member2.getMemberId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);
        //삭제 검증 memberRepository.delete(member1); memberRepository.delete(member2);
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByNameAndEmail() {
        Member m1 = new Member("AAA", "aaa@gmail.com");
        Member m2 = new Member("BBB", "bbb@gmail.com");
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findByNameAndEmail("AAA", "aaa@gmail.com");
        assertThat(result.get(0).getName()).isEqualTo("AAA");
        assertThat(result.get(0).getEmail()).isEqualTo("aaa@gmail.com");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("AAA", "aaa@gmail.com");
        Member m2 = new Member("BBB", "bbb@gmail.com");
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findMember("BBB", "bbb@gmail.com");
        assertThat(result.get(0)).isEqualTo(m2);
    }

}