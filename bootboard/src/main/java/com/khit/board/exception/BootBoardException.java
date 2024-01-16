package com.khit.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//사용자 Exception RuntimeException을 상속 받아야 함
//@ResponseStatus 404 오류 표시 클래스 사용
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BootBoardException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	//생성자
	public BootBoardException(String message) {
		super(message);
	}
}
