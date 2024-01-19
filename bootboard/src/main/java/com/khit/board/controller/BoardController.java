package com.khit.board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.khit.board.dto.BoardDTO;
import com.khit.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;
	
	//글쓰기 페이지
	@GetMapping("/write")
	public String writeForm(BoardDTO boardDTO) {
		
		return "/board/write";	//write.html
	}
	
	//글쓰기 처리
	@PostMapping("/write")
	public String write(@Valid BoardDTO boardDTO,
			BindingResult bindingResult,
			MultipartFile boardFile) throws Exception {
		if(bindingResult.hasErrors()) {	//에러가 있으면 글쓰기폼으로 이동
			log.info("has errors");
			return "/board/write";
		}
		boardService.save(boardDTO, boardFile);
		return "redirect:/board/pagelist";
	}
	
	//글 목록
	@GetMapping("/list")
	public String getList(Model model) {
		List<BoardDTO> boardDTOList = boardService.findAll();
		model.addAttribute("boardList", boardDTOList);
		return "/board/list";
	}
	
	//글 목록(페이지)
	// /board/pagelist?page=0
	// /board/pagelist
	@GetMapping("/pagelist")
	public String getPageList(
			@RequestParam(value="type", required = false) String type,
			@RequestParam(value="keyword", required = false) String keyword,
			@PageableDefault(page=1) Pageable pageable,
			Model model) {
		//검색어가 없으면 페이지 처리, 있으면 검색 처리
		Page<BoardDTO> boardDTOList = null;
		if(keyword == null) {
			boardDTOList = boardService.findListAll(pageable);
			
		}else if(type != null && type.equals("title")){
			boardDTOList = boardService.findByBoardTitleContaining(keyword, pageable);
		}else if(type != null && type.equals("content")){
			boardDTOList = boardService.findByBoardContentContaining(keyword, pageable);
		}
		//하단의 페이지 블럭 만들기
		int blockLimit = 10;	//하단에 보여줄 페이지 개수
		//시작 페이지 1, 11, 21, 	12/10 = 1.2 -> 2.2 -> 2-1, 1 * 10 + 1 =11
		int startPage = ((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit))-1)*blockLimit+1;
		//마지막 페이지 10, 20, 30	//12page -> 12 마지막
		int endPage = (startPage+blockLimit-1) > boardDTOList.getTotalPages() ?
			boardDTOList.getTotalPages() : startPage + blockLimit-1;
		
		model.addAttribute("boardList", boardDTOList);
		model.addAttribute("type", type);
		model.addAttribute("kw", keyword);	//검색어 보내기
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "/board/pagelist";
	}
	
	//글 상세보기
	@GetMapping("/{id}")
	public String getBoard(
			@PathVariable Long id,
			@PageableDefault(page=1) Pageable pageable,
			Model model) {
		//조회수
		boardService.updateHits(id);
		
		//글 상세보기
		BoardDTO boardDTO  = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", pageable.getPageNumber());
		return "/board/detail";
	}
	
	//글 삭제하기
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.deleteById(id);
		return "redirect:/board/list";
	}
	
	//글 수정 페이지
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id,
			Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		return "/board/update";
	}
	
	//글 수정 처리
	@PostMapping("/update")
	public String update(@ModelAttribute BoardDTO boardDTO) {
		//수정 후 글 상세보기
		boardService.update(boardDTO);
		return "redirect:/board/" + boardDTO.getId();
	}
		

}
