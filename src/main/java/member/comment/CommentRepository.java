package member.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository  extends JpaRepository<Comment, Long>, CommentCustomRepository {
	
	 
//	@Query(value="SELECT c  "
//				+ "from Comment c WHERE c.seq = :board", nativeQuery = true)
//	List<CommentDto> findAllBySeq(@Param("board") long board);
	
//	@Query(value="select new member.comment.CommentDto(c.member_id, c.comment_content,c.ins_date, t.seq) from COMMENT c join c.seq t WHERE c.boardNo = :boardNo",nativeQuery = true)
	List<Comment> findByBoardNo(@Param("boardNo") Long boardNo);

	@Transactional
	@Modifying
	@Query("UPDATE Comment c SET c.commentContent = :commentContent WHERE c.commentId = :commentId ")
	int modify(Long commentId, String commentContent);

	Comment findByCommentId(long superCommentId);
	
	
	
}
