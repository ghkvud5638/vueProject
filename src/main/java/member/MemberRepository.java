package member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	@Query("select m from Member m where email = :email and password = :password")
	Member findMember(String email, String password);

	Member findByEmail(String email);
	
	@Query("select m from Member m where seq = :memberNo")
	Member findByMemberNo(Long memberNo);
	
	@Transactional
	@Modifying
	@Query("UPDATE Member m set m.refreshToken = :refreshToken where m.seq = :seq ")
	void updateRefreshToken(Long seq, String refreshToken);

	@Query("select m from Member m where m.email = :email")
	Member findByUserId(String email);

	@Transactional
	@Modifying
	@Query("UPDATE Member m SET m.name = :name where m.email = :email ")
	void modifyUserInfo(String email, String name);
}


//@Query("UPDATE Board b SET b.boardHits = b.boardHits + 1 where b.seq = :seq ")
//void updateHits(@Param("seq") Long seq);
