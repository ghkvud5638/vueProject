package member.comment;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import member.Member;
import member.MemberRepository;
import member.board.Board;
import member.board.BoardDto;
import member.board.BoardRepository;

@Slf4j
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

//	@Autowired
//	private BoardService boardService;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	CommentRepository commentRepository;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/commentApply")
	@ResponseBody
	public CommentDto commentApply(@RequestBody CommentDto comment, Principal principal) {

		BoardDto boardDetail = new BoardDto();
		Board detail = boardRepository.findById(comment.getBoardNo());
		boardDetail.setBoardTitle(detail.getBoardTitle());
		boardDetail.setBoardContent(detail.getBoardContent());
		boardDetail.setSeq(detail.getSeq());
		boardDetail.setBoardHits(detail.getBoardHits());
		boardDetail.setInsDate(detail.getInsDate());
		boardDetail.setWriter(detail.getWriter());

		Member member = memberRepository.findByEmail(principal.getName());
		comment.setBoard(boardDetail);
		comment.setMember(member);
		comment.setMemberNo(member.getSeq());
		comment.setInsDate(new Date());
		// CommentDto 세팅

		// CommentDto -> Comment 변환
		Comment c = new Comment();
		c.setBoard(detail);
		c.setCommentContent(comment.getCommentContent());
		c.setInsDate(comment.getInsDate());
		c.setMember(comment.getMember());
		c.setBoardNo(comment.getBoardNo());
		c.setMemberNo(comment.getMemberNo());

		// Comment 세팅 완료

		Comment cDetail = commentService.applyComment(c);
		CommentDto res = new CommentDto();
		res.setCommentContent(cDetail.getCommentContent());
		res.setMember(cDetail.getMember());
		res.setCommentId(cDetail.getCommentId());
		res.setInsDate(cDetail.getInsDate());
		return res;
	}
	
	@PostMapping("/comment/recomment")
	@ResponseBody
	public CommentDto recommentApply(@RequestBody CommentDto comment, Principal principal) {
//		log.info("reComment : "+comment);
		Comment res = commentService.applyRecomment(comment,principal);
		CommentDto resDto = new CommentDto();
		
		resDto.setCommentContent(res.getCommentContent());
		resDto.setMember(res.getMember());
		resDto.setCommentId(res.getCommentId());
		resDto.setInsDate(res.getInsDate());
		resDto.setLevel(res.getLevel());
		resDto.setSuperCommentId(res.getSuperCommentId());
		
		System.out.println("resDto : "+resDto);
		
		return  resDto;
	}
 

	@PostMapping("/commentDelete")
	@ResponseBody
	public int commentDelete(@RequestBody CommentDto comment) {
//		log.info("시퀀스 " + comment.getCommentId());
		commentService.commentDelete(comment.getCommentId());
		return 0;
	}


	@PostMapping("/commentModify")
	@ResponseBody
	public int modifyComment(@RequestBody CommentDto comment) {
		log.info("comment : "+comment);
		Comment c = new Comment();
		c.setCommentId(comment.getCommentId());
		c.setCommentContent(comment.getCommentContent());
		return commentService.modifyComment(c); 
	}
	
	@RequestMapping("/commentAreaHtml")
	public void commentAreaHtml() {
		
	}
	
	
}
