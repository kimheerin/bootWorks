package com.khit.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.khit.board.dto.BoardDTO;
import com.khit.board.entity.Board;
import com.khit.board.exception.BootBoardException;
import com.khit.board.repository.BoardReposiroty;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardReposiroty boardReposiroty;

	public void save(BoardDTO boardDTO, MultipartFile boardFile) throws Exception {
		//1. 파일 서버에 저장
		if(!boardFile.isEmpty()) {	//전달된 파일이 있으면
			//저장 경로
			String filepath = "C:\\bootWorks\\bootboard\\src\\main\\resources\\static\\upload\\";
			
			UUID uuid = UUID.randomUUID();	//무작위 데이터 생성
			
			String filename = uuid + "_" + boardFile.getOriginalFilename();	//원본 파일
			
			//File 클래스 객체 생성
			File savedFile = new File(filepath, filename);	//upload 폴더에 저장
			boardFile.transferTo(savedFile);
			
		//2.파일명 db에 저장
		boardDTO.setFilename(filename);
		boardDTO.setFilepath("/upload/" + filename);	//파일 경로 설정
	}
		
		//dto -> entity로 변환
		Board board = Board.toSaveEntity(boardDTO);
		//entity를 db에 저장
		boardReposiroty.save(board);
	}

	public List<BoardDTO> findAll() {
		//db에서 entity list를 가져옴
		//List<Board> boardList = boardReposiroty.findAll();
		//내림차순 정렬- Sort.by(정렬 방식, 해당 필드)
		List<Board> boardList =
				boardReposiroty.findAll(Sort.by(Sort.Direction.DESC, "id"));
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

	public Page<BoardDTO> findListAll(Pageable pageable) {
		int page = pageable.getPageNumber() -1;	//db가 1 작음
		int pageSize = 10;
		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		
		Page<Board> boardList = boardReposiroty.findAll(pageable);
		
		log.info("boardList.isFirst()=" + boardList.isFirst());
		log.info("boardList.isLast()=" + boardList.isLast());
		log.info("boardList.getNumber()=" + boardList.getNumber());
		
		//생성자 방식으로 boardDTOLis 만들기
		Page<BoardDTO> boardDTOList = boardList.map(board ->
		 		new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
		 				board.getBoardContent(), board.getBoardHits(),board.getFilename(), 
		 				board.getFilepath(), board.getCreatedDate(), board.getUpdatedDate()));
		
		return boardDTOList;
	}

	public Page<BoardDTO> findByBoardTitleContaining(String keyword, Pageable pageable) {
		int page = pageable.getPageNumber() -1;	//db가 1 작음
		int pageSize = 10;
		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		
		Page<Board> boardList = boardReposiroty.findByBoardTitleContaining(keyword, pageable);
		
		//생성자 방식으로 boardDTOLis 만들기
		Page<BoardDTO> boardDTOList = boardList.map(board ->
		 		new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
		 				board.getBoardContent(), board.getBoardHits(),board.getFilename(), 
		 				board.getFilepath(), board.getCreatedDate(), board.getUpdatedDate()));
		
		return boardDTOList;
	}

	public Page<BoardDTO> findByBoardContentContaining(String keyword, Pageable pageable) {
		int page = pageable.getPageNumber() -1;	//db가 1 작음
		int pageSize = 10;
		pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		
		Page<Board> boardList = boardReposiroty.findByBoardContentContaining(keyword, pageable);
		
		//생성자 방식으로 boardDTOLis 만들기
		Page<BoardDTO> boardDTOList = boardList.map(board ->
		 		new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
		 				board.getBoardContent(), board.getBoardHits(),board.getFilename(), 
		 				board.getFilepath(), board.getCreatedDate(), board.getUpdatedDate()));
		
		return boardDTOList;
	}
}
