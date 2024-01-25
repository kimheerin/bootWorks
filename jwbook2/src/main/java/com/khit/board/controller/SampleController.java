package com.khit.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khit.board.entity.Users;

@RestController // 데이터 전달이 역할인 어노테이션
public class SampleController {
   
	// get - select
	// 객체를 반환하면 - json 형태로 전달됨
	@GetMapping("/khit")
	public String httpGet() {
		// 유저 1명 생성 후 데이터 검색(보기)
		// Users user = new Users();
		// user.setId(user.getId());
		Users user = Users.builder().id(1).username("today").password("1234").email("cloud@sky.com").build();

		return user + "회원 보기";
	}

	// Post - Insert
	// 전달받은 데이터가 JSON 형태({key:value})일 때 @Rquestbody를 사용
	@PostMapping("/khit")
	public String httpPost(@RequestBody Users users) {
		return "Post 요청 처리 " + users.toString();
	}

	// Put - update
	@PutMapping("/khit")
	public String httpPut(@RequestBody Users users) {
		return "Put 요청 처리 " + users.toString();
	}

	// Delete - delete
	// @PathVariable 경로 변수를 전달 받음
	@DeleteMapping("/khit/{id}")
	public String httpDelete(@PathVariable Integer id) {
		return "Delete 요청 처리 " + id;
	}
}