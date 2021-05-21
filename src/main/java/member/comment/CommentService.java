package member.comment;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import member.Member;
import member.MemberRepository;
import member.board.Board;
import member.board.BoardDto;
import member.board.BoardRepository;

@Slf4j
@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private CommentCustomRepositoryImpl commentCustomRepositoryImpl;

	
	

	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}  
 
	public Comment applyComment(Comment comment) {
//		log.info("applyComment : "+comment);		
		return commentRepository.save(comment);
	}
 
	public Comment applyRecomment(CommentDto comment, Principal principal) {
		BoardDto boardDetail = new BoardDto();
		Board detail = boardRepository.findById(comment.getBoardNo());
		boardDetail.setBoardTitle(detail.getBoardTitle());
		boardDetail.setBoardContent(detail.getBoardContent());
		boardDetail.setSeq(detail.getSeq());
		boardDetail.setBoardHits(detail.getBoardHits());
		boardDetail.setInsDate(detail.getInsDate());
		boardDetail.setWriter(detail.getWriter());
		comment.setBoard(boardDetail);
		
		Comment c = new Comment();
		c.setBoardNo(comment.getBoardNo());
		Member member = memberRepository.findByEmail(principal.getName());
		c.setMember(member); //현재로그인해서 댓글을 등록한 id 찾아오기
		c.setMemberNo(member.getSeq());
		c.setCommentContent(comment.getCommentContent());
		c.setInsDate(new Date());
		c.setBoard(detail);
		
		Comment superComment = commentRepository.findByCommentId(comment.getSuperCommentId()); //넘어온 superCommentId로 특정 부모 댓글 객체 받아오기
		c.setSuperComment(superComment);
		c.setSuperCommentId(superComment.getCommentId());
		c.setLevel(comment.getLevel());

		return commentRepository.save(c);
	}

	public List<Comment> getCommentList(long boardNo) { 
//		log.info("getCommentList");
//		BoardDto bDto = new BoardDto();  
//		bDto.setSeq(board.getSeq());  
//		List<Comment> c = commentRepository.findByBoardNo(boardNo);		
		List<Comment> c = commentCustomRepositoryImpl.findByBoardNo(boardNo);		
		System.out.println("여기 "+c);
		
		
		return c;
	}
	

	public void commentDelete(long seq) {
		commentRepository.deleteById(seq);
	}

	public int modifyComment(Comment comment) {
		return commentRepository.modify(comment.getCommentId(), comment.getCommentContent());
	}


}
