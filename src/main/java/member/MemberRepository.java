package member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	@Query("select m from Member m where email = :email and password = :password")
	Member findMember(String email, String password);

	Member findByEmail(String email);
	
	@Query("select m from Member m where seq = :memberNo")
	Member findByMemberNo(Long memberNo);
}
