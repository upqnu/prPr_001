package upqnu.prPr.member.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import upqnu.prPr.member.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*; // ~Assertions. 다음에 *이 와야 이 클래스에 속한 메서드를 정상적으로 사용할 수 있음.


class MemberMvcRepositoryTest {

    MemberMvcRepository memberMvcRepository = new MemberMvcRepository();

    @AfterEach
    void afterEach() {
        memberMvcRepository.clearMember();
    }

    @Test
    void save() {
        Member member = new Member("aaa", "aaa.gmail.com");

        Member savedMember = memberMvcRepository.save(member);

        Member findMember = memberMvcRepository.findById(member.getMemberId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findById() {
        Member member = new Member("aaa", "aaa.gmail.com");
        Member savedMember = memberMvcRepository.save(member);

        Member findMember = memberMvcRepository.findById(savedMember.getMemberId());

        assertThat(findMember.getMemberId()).isEqualTo(savedMember.getMemberId());
        assertThat(findMember.getName()).isEqualTo(savedMember.getName());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        Member member1 = new Member("aaa", "aaa.gmail.com");
        Member member2 = new Member("bbb", "bbb.gmail.com");
        Member savedMember1 = memberMvcRepository.save(member1);
        Member savedMember2 = memberMvcRepository.save(member2);

        List<Member> members = memberMvcRepository.findAll();

        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1, member2);
    }

    @Test
    void updateMember() {
        Member member = new Member("aaa", "aaa.gmail.com");
        Member savedMember = memberMvcRepository.save(member);
        Long memberId = savedMember.getMemberId();

        Member updatedMember = new Member("bbb", "bbb@gmail.com");
        memberMvcRepository.update(memberId, updatedMember);

        Member findMember = memberMvcRepository.findById(memberId);
        assertThat(findMember.getName()).isEqualTo(updatedMember.getName());
        assertThat(findMember.getPassword()).isEqualTo(updatedMember.getPassword());
    }

}