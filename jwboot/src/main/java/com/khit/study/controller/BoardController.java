package com.khit.study.controller;

import org.springframework.web.bind.annotation.RestController;

import com.khit.study.entity.BoardVO;
import com.khit.study.service.BoardService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


//문자열을 반환하는 이노테이션
//@ResponseBody, @ResponseEntity와 비슷함
@AllArgsConstructor
@RestController
public class BoardController {
	
	private BoardService boardService;
	
	@GetMapping("/greeting")
	public String sayHello(String name) {
		return "hello " + name;		//문자열
	}
	
	//객체 데이터를 브라우저에 보냄
	@GetMapping("/board/detail")
	public BoardVO getBoard() {
		BoardVO board = boardService.getBoard();
		return board;
	}
	
   @GetMapping("/board/list")
   public List<BoardVO> getBoardList(){
      List<BoardVO> boardList = boardService.getBoardList();
      return boardList;
   }
}
