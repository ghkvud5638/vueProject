package member.board;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;  

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Page<Board> getBoardList(Pageable pageable, String searchText) {
		// page는 index 처럼 0부터 시작
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 10, Sort.by("seq").descending()); // <- Sort 추가		
		
		if(searchText == null) { 
			return boardRepository.findAll(pageable); 
			 
		}else {
			return boardRepository.findByBoardTitleContaining(pageable, searchText); 
		} 

	}

	public int writeProc(Board board) {
		Board b = boardRepository.save(board);
		int res = 0;
		if (b.getSeq() != 0 && b.getWriter() != null) {
			return res = 1;
		}
		return res;
	}

	public Board boardDetail(Board board) {
		Board detail = boardRepository.getOne(board.getSeq());
		boardRepository.updateHits(board.getSeq());
		return detail;
	}

	public int modify(Board board) {
		int res = boardRepository.modify(board.getSeq(), board.getBoardTitle(), board.getBoardContent());
		return res;
	}

	public void delete(Board board) {
		boardRepository.deleteById(board.getSeq());
	}

	public List<Board> getBoardVueList(String searchText) {
		if(searchText == null) {
			return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "seq"));
			
		}else { 
			return boardRepository.findByBoardTitleContaining(searchText);
		}
	}

	public Board getBoardDetail(long seq) {
		return boardRepository.getOne(seq);
	}
 
	public void insertBoardVue(Board board) {
		boardRepository.save(board);
	} 
	public void deleteBoard(Board board) {
		boardRepository.delete(board);
	}

	public void modifyBoard(BoardDto board) {
		boardRepository.modifyBoard(board.getSeq(),board.getBoardTitle(),board.getBoardContent());
	}


	



//	public Page<Board> getBoardSearchList(Pageable pageable, String searchText) {
//
//		// page는 index 처럼 0부터 시작
//		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
//		pageable = PageRequest.of(page, 10, Sort.by("seq").descending()); // <- Sort 추가
// 
//		return boardRepository.findByBoardTitleContaining(pageable, searchText); 
//	} 
}
