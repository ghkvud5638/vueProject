package member.comment;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import member.Member;
import member.board.BoardDto;

@Data
@AllArgsConstructor
@ToString(of= {"commentId","commentContent","insDate","boardNo","superComment","member","level","superCommentId"}) 
public class CommentDto {
	 private long commentId;
	 private String commentContent;
	 private BoardDto board;
	 private Member member;
	 private Date insDate;
	 private long boardNo;
	 private long memberNo;	 
	 private String memberId;
	 private Comment superComment;
	 private long superCommentId; 
	 private int level;
	 
	 public CommentDto() {
	 };
	
}
