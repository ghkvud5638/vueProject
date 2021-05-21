package member.comment;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import member.board.Board;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Comment> findByBoardNo(Long boardNo) {
		List<Comment> comments = jdbcTemplate.query("SELECT"+
								"      CASE"+
								"			WHEN level - 1 > 0 then CONCAT(CONCAT(REPEAT('  ', level - 1), '┗'), c.comment_content)"+
								"			ELSE c.comment_content"+
								"		END AS comment_content"+
								"		,c.comment_id"+
								"		,c.super_comment_id"+
								"		,t.level"+
								"		,c.ins_date"+
								"		,c.board_no"+
								"		,c.member_no"+
								"	FROM ("+
								"		SELECT hieararchial_comment() AS id, @level AS level, @rownum:=@rownum+1 AS rn"+
								"		FROM ("+
								"			SELECT"+
								"				@start_with := 0"+
								"			   ,@id := @start_with"+
								"			   ,@level := 0"+
								"			 ) vars,"+
								"			 (SELECT @rn :=0) rr"+
								"			 JOIN COMMENT"+
								"			 WHERE"+
								"					(@rownum:=0)=0"+
								"		) t"+
								"	JOIN COMMENT c ON t.id = c.comment_id"+
								"	ORDER BY rn",commentMapper);
		return comments;
	}
	private RowMapper<Comment> commentMapper = (rs, rowNum)-> Comment.builder()
											.commentId(rs.getLong("comment_id"))
											.commentContent(rs.getString("comment_content"))
											.boardNo(rs.getLong("board_no"))
											.memberNo(rs.getLong("member_no"))
											.superCommentId(rs.getLong("super_comment_id"))
											.level(rs.getInt("level"))
											.build();

}
