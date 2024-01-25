package com.khit.board.exception;

//사용자 정의 예외 메서드
public class CustomException extends RuntimeException{

	public CustomException(String message) {
		super(message);
	}

}
