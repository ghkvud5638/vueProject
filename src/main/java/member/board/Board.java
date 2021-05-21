package member.board;

import java.util.Date;
import java.util.List;

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
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import member.Member;
import member.comment.Comment;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BOARD")
public class Board { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq") 
	private long seq;

	private String boardTitle;
	private String boardContent;
	private Integer boardHits;

	@Column(name = "INS_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date insDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="board")
	private List<Comment> comment; 
 
	@Builder
	public Board(@NotEmpty String boardTitle, @NotEmpty String boardContent, String boardHits, Member writer) {
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardHits = 0;
		this.writer = writer;
		this.insDate = new Date();
	}
}
