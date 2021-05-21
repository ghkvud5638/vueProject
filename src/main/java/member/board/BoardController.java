package member.board;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import member.Member;
import member.MemberRepository;
import member.comment.Comment;
import member.comment.CommentDto;
import member.comment.CommentRepository;
import member.comment.CommentService;

@Slf4j
@RestController
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;


	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("/board")
	public String boardView(@PageableDefault Pageable pageable, Model model, String searchText) {
		Page<Board> boardList = boardService.getBoardList(pageable, searchText);		
//		log.info("boardList here : " + boardList);
		
		List<BoardDto> res = new ArrayList<BoardDto>();
		List<Board> boardContents = boardList.getContent();
		
		for(Board bl : boardContents) {
			BoardDto dto = new BoardDto();
			dto.setBoardTitle(bl.getBoardTitle());
			dto.setBoardContent(bl.getBoardContent());
			dto.setSeq(bl.getSeq());
			dto.setBoardHits(bl.getBoardHits());
			dto.setInsDate(bl.getInsDate());
//			dto.setComment(bl.getComment());
			dto.setWriter(bl.getWriter());
			
			res.add(dto);
		}
		
		
		model.addAttribute("boardList", res);
		model.addAttribute("paging", boardList);
		
		if(searchText != null) {
			model.addAttribute("searchText",searchText);
		}
//		log.info(
//				"총 element 수 : {}, 전체 totalPages 수 : {}, 페이지에 표시할 element 수 : {}, "
//				+ "현재 페이지 index : {}, 현재 페이지의 element 수 : {}, number:{}, boardList : {}",
//				boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(), boardList.getNumber(),
//				boardList.getNumberOfElements(), boardList.getNumber(), boardList.getContent());

		return "/board";
	}

	@GetMapping("/write")
	public String write() {
		return "/write";
	}

	@PostMapping("/writeProc")
	@ResponseBody
	public int writeProc(@RequestBody BoardDto board, Principal principal) {
		
		Board b = new Board();
		b.setBoardTitle(board.getBoardTitle());
		b.setBoardContent(board.getBoardContent());
		b.setInsDate(new Date());
		b.setWriter(memberRepository.findByEmail(principal.getName()));
		b.setBoardHits(0);
		
		int res = boardService.writeProc(b);

		return res;
	}

	@GetMapping("/board/detail")
	public String detail(BoardDto board, Model model) {
		Board detail = boardService.boardDetail(transferBoardToBoardDto(board));
		List<CommentDto> commentList = getCommentList(board.getSeq());
//		System.out.println("commentList : "+commentList);
		
		//commentList member가 null임
		
		model.addAttribute("detail", detail); 
		model.addAttribute("commentList", commentList); 
//		log.info("commentList : "+ commentList);
		return "/boardDetail";
	}

	private List<CommentDto> getCommentList(long seq) {
		List<Comment> commentList = commentService.getCommentList(seq);
//		System.out.println("여기! "+commentList);
		List<CommentDto> res = new ArrayList<>();
		for(Comment c:commentList) {
			CommentDto cd = new CommentDto();
			cd.setCommentId(c.getCommentId());
			cd.setBoardNo(c.getBoardNo());
			cd.setCommentContent(c.getCommentContent());
			cd.setMember(c.getMember());
			cd.setMemberNo(c.getMemberNo()); 
			Member member = memberRepository.findByMemberNo(c.getMemberNo()); 
			cd.setMember(member);
			cd.setMemberId(member.getEmail());
			cd.setInsDate(c.getInsDate());
			cd.setSuperCommentId(c.getSuperCommentId());
			Comment superComment = commentRepository.findByCommentId(c.getSuperCommentId());
			cd.setSuperComment(superComment);
			cd.setLevel(c.getLevel());
			res.add(cd);
		}
		System.out.println("여기! fuck! "+res);
		return res;
	}

	@GetMapping("/modify")
	public String modify(BoardDto board, Model model) {
		Board detail = boardService.boardDetail(transferBoardToBoardDto(board));
		model.addAttribute("detail", detail);
		return "/modify";
	}

	@PostMapping("/modifyProc")
	@ResponseBody
	public int modifyProc(@RequestBody BoardDto board) {
		Board transBoardDetail = new Board();
		transBoardDetail.setSeq(board.getSeq());
		transBoardDetail.setBoardTitle(board.getBoardTitle());
		transBoardDetail.setBoardContent(board.getBoardContent());
		
		int res = boardService.modify(transBoardDetail);
		return res;
	}
//
	@PostMapping("/delete") 
	@ResponseBody
	public int delete(@RequestBody BoardDto board) {
		Board transBoardDetail = new Board();
		transBoardDetail.setSeq(board.getSeq());

		boardService.delete(transBoardDetail);
		return 0;
	}
	

	@RequestMapping("/vue")
	public String vue() {
		return "/vueMain";
	}
	
	
	public Board transferBoardToBoardDto(BoardDto board) {
		Board transBoardDetail = new Board();
		transBoardDetail.setSeq(board.getSeq());

		return transBoardDetail;
	}

	@RequestMapping("/boardVue")
	public List<BoardDto> getBoardList(){
		System.out.println("im here");
		List<Board> boardList = boardService.getBoardVueList();
		
		List<BoardDto> boardListDto = new ArrayList<>();
		for(Board bl : boardList) {
			BoardDto bDto = new BoardDto();			
			bDto.setSeq(bl.getSeq());
			bDto.setBoardTitle(bl.getBoardTitle());
			bDto.setWriterName(bl.getWriter().getName());
			bDto.setInsDate(bl.getInsDate());
			bDto.setBoardHits(bl.getBoardHits());
			boardListDto.add(bDto);
		}
		
		
		System.out.println(" boardListDto : " +  boardListDto);
		return boardListDto;
	}
	
	
	@RequestMapping("/detailVue/{seq}")
	public BoardDto detailVue(@PathVariable long seq) {
//		System.out.println("here!"+seq);
		Board b = boardService.getBoardDetail(seq);
		
		BoardDto boardDetail = new BoardDto();
		boardDetail.setBoardTitle(b.getBoardTitle());
		boardDetail.setBoardContent(b.getBoardContent());
		boardDetail.setBoardHits(b.getBoardHits());
		boardDetail.setInsDate(b.getInsDate());
		boardDetail.setSeq(b.getSeq());
		boardDetail.setWriter(b.getWriter());
		boardDetail.setWriterName(b.getWriter().getName());
		
		System.out.println("boardDetail : "+boardDetail);
		
		return boardDetail;
	}
	
	@RequestMapping("/board/boardCreateVue")
	public void boardCreateVue(@RequestBody Board board) {
		long num = 1;
		board.setWriter(memberRepository.findByMemberNo(num)); // 글 작성시 작성자 하드코딩
		
		board.setInsDate(new Date());
//		b.setWriter(memberRepository.findByEmail(principal.getName()));
		board.setBoardHits(0);

		System.out.println("create here : " + board);
		boardService.insertBoardVue(board);
		
	}
	
	
}
