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

	// FetchType은 JPA가 하나의 Entity를 조회할 떄, 연관관계에 있는 객체들을 어떻게 가져올 것이냐를 나타내는 설정값.
	// Eager 전략 -> 연관관계에 있는 Entity 값들을 모두 가져온다.
	// Lazy 전략 -> 연관관계에 있는 Entity를 가져오지 않고, getter로 접근할 때 가져온다.
	@ManyToOne(fetch = FetchType.LAZY) // ManyToOne-> Board Entity는 
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
