package upqnu.prPr.member.repository;

import org.springframework.stereotype.Repository;
import upqnu.prPr.member.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberMvcRepository {

    private static final Map<Long, Member> memberGroup = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setMemberId(++sequence);
        memberGroup.put(member.getMemberId(), member);
        return member;
    }

    public Member findById(Long memberId) {
        return memberGroup.get(memberId);
    }

    public List<Member> findAll() {
        return new ArrayList<>(memberGroup.values());
    }

    public void update(Long memberId, Member updatedMember) {
        Member findMember = findById(memberId);
        findMember.setName(updatedMember.getName());
        findMember.setPassword(updatedMember.getPassword());
    }

    public void clearMember() {
        memberGroup.clear();
    }
}
