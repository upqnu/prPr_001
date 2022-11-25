package upqnu.prPr.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upqnu.prPr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}