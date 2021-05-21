package member.board;

import java.util.Date;
import java.util.List;

import lombok.Data;
import member.Member;
import member.comment.Comment;

@Data
public class BoardDto {
	
	private long seq;
	private String boardTitle;
	private String boardContent;
	private Date insDate;
	private Member writer;
	private List<Comment> comment;
	private Integer boardHits;
	private String writerName;

}
