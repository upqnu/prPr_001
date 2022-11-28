package upqnu.prPr.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import upqnu.prPr.member.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameAndEmail(String name, String email);

    @Query("select m from Member m where m.name = :name and m.email = :email")
    List<Member> findMember(@Param("name") String name, @Param("email") String email);
}