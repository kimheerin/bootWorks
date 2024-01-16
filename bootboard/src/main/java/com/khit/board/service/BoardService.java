package com.khit.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.khit.board.dto.BoardDTO;
import com.khit.board.entity.Board;
import com.khit.board.exception.BootBoardException;
import com.khit.board.repository.BoardReposiroty;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardReposiroty boardReposiroty;

	public void save(BoardDTO boardDTO) {
		//dto -> entity로 변환
		Board board = Board.toSaveEntity(boardDTO);
		//entity를 db에 저장
		boardReposiroty.save(board);
	}

	public List<BoardDTO> findAll() {
		//db에서 entity list를 가져옴
		List<Board> boardList = boardReposiroty.findAll();
		//빈 리스트 생성
		List<BoardDTO> boardDTOList = new ArrayList<>();
		
		for(Board board : boardList) {
			//entity -> dto 변환
			BoardDTO boardDTO = BoardDTO.toSaveDTO(board);
			boardDTOList.add(boardDTO);
		}
		return boardDTOList;
	}

	public BoardDTO findById(Long id) {
		Optional<Board> findBoard = boardReposiroty.findById(id);
		if(findBoard.isPresent()) {	//검색한 게시글이 없으면 DTO를 컨트롤러로 반환
			BoardDTO boardDTO = BoardDTO.toSaveDTO(findBoard.get());
			return boardDTO;
		}else {
			throw new BootBoardException("게시글을 찾을 수 없습니다.");
		}
	}
	
	@Transactional	//컨트롤러리 작업(메서드 2개 이상)
	public void updateHits(Long id) {
		//메서드를 boardRepository에 생성
		boardReposiroty.updateHits(id);
	}
	
	public void deleteById(Long id) {
		boardReposiroty.deleteById(id);
	}

	public void update(BoardDTO boardDTO) {
		//save() - 삽입(id가 없고), 수정(id가 있음)
		//dto -> entity
		Board board = Board.toUpdateEntity(boardDTO);
		boardReposiroty.save(board);
	}
}
