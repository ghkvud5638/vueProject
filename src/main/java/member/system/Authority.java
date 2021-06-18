package member.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import member.Member;
import member.board.Board;
import member.comment.Comment;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AUTHORITY")
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq") 
	private long groupauthId;
	
	//그룹명
	private String groupauthNm;
	
	//설명
	private String commentTxt;

	//사용여부
	private String useYn;
	
	@Builder
	public Authority(String groupauthNm, String commentTxt, String useYn) {
		this.groupauthNm = groupauthNm;
		this.commentTxt = commentTxt;
		this.useYn = useYn;
	}

}
