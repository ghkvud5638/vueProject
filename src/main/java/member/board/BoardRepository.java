package member.board;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
	@Transactional
	@Modifying
	@Query("UPDATE Board b SET b.boardHits = b.boardHits + 1 where b.seq = :seq ")
	void updateHits(@Param("seq") Long seq);

	@Transactional
	@Modifying
	@Query("UPDATE Board b SET b.boardTitle = :boardTitle, b.boardContent = :boardContent where b.seq = :seq ")
	int modify(@Param("seq") Long seq, @Param("boardTitle") String boardTtitle, @Param("boardContent") String boardContent);

	
//	@Query(
//			value= "SELECT b from Board b WHERE b.boardTitle LIKE %:searchText%",
//			countQuery = "SELECT COUNT(b.seq) FROM Board b WHERE b.boardTitle LIKE %:searchText%"
//	)
	Page<Board> findByBoardTitleContaining(Pageable pageable, String searchText);
	
	Board findById(long id);


	@Transactional
	@Modifying
	@Query("UPDATE Board b SET b.boardTitle = :boardTitle, b.boardContent = :boardContent where b.seq = :seq ")
	void modifyBoard(@Param("seq") Long seq, @Param("boardTitle") String boardTtitle, @Param("boardContent") String boardContent);
	
	
	
}
