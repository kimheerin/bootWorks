package com.khit.board.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Controller //예외 처리 클래스
@RestController //문자 반환 클래스
public class BootBoardExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public String globalExceptionHandler(Exception e) {
		return "<h2>" + e.getMessage() + "</h2>";
	}
}
