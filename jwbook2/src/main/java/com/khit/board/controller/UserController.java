package com.khit.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khit.board.entity.User;
import com.khit.board.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	
	//회원 가입
	//포스트맨에 JSON형태의 자료를 입력 받아서 db에 저장
	@PostMapping("/user")
	public @ResponseBody String insertUser(@RequestBody User user) {
		userService.save(user);
		return "회원 가입 성공 ";
	}
	
	//회원 목록 보기
	@GetMapping("/user/list")
	public @ResponseBody List<User> getList() {
		List<User> userList = userService.findAll();
		return userList;
	}
	
	//회원 상세보기
	//아이디로 검색
	@GetMapping("/user/{id}")
	public @ResponseBody User getUser(@PathVariable Integer id) {
		//검색된 회원이 없을 경우 예외 반환
		User findUser = 
				userService.findById(id);
		return findUser;
	}
	
}
