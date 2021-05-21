package member.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import member.Member;
import member.board.Board;

@Data 
@NoArgsConstructor   
@Entity    
@Table(name = "COMMENT")  
@ToString(of= {"commentId","commentContent","insDate","boardNo","level"}) 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Comment {  
	 
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "comment_id")  
	private long commentId;  
	  
	private String commentContent; 
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seq")
	private Board board; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; 
	
	private long memberNo;
	
	private Date insDate;  
	
	private long boardNo; 
	 
	@ManyToOne(fetch=FetchType.LAZY)  
	@JoinColumn(name="super_comment") 
	private Comment superComment; 
	 
	private long superCommentId;
	
	 @OneToMany(mappedBy = "superComment", cascade = CascadeType.ALL) 
	private List<Comment> subComment = new ArrayList<>(); 
	
	//댓글을 삭제할 경우, 하위 댓글들도 삭제를 해야하는가?
	private boolean isLive;
	
	@Transient
	private int level; 
	
	
	
	@Builder
	public Comment(
			String commentContent, Board board 
			, Member member, long boardNo, long memberNo,long superCommentId,long commentId,int level) {
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.board = board; 
		this.member = member; 
		this.insDate = new Date(); 
		this.boardNo = boardNo; 
		this.superCommentId = superCommentId;
		this.memberNo = memberNo;
		this.level = level;
	}
	
}
