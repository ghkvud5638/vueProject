package member.comment;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface CommentCustomRepository {
	
	List<Comment> findByBoardNo(@Param("boardNo") Long boardNo);
}
