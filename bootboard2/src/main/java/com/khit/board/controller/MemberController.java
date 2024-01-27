package com.khit.board.controller;

import com.khit.board.config.SecurityUser;
import com.khit.board.dto.MemberDTO;
import com.khit.board.entity.Member;
import com.khit.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	// 메인페이지
	@GetMapping("/main")
	public String main() {
		return "main";
	}

	// 로그인
	@GetMapping("/member/login")
	public String loginForm() {
		return "/member/login";
	}

	/*
	 * @PostMapping("/member/login") public String login(@ModelAttribute Member
	 * member, HttpSession session){ Member loginMember =
	 * memberService.login(member); if(loginMember != null &&
	 * loginMember.getPassword().equals(member.getPassword())){ //아이디와 비밀번호 일치해서 로그인
	 * 되면 세션 발급 session.setAttribute("sessMemberId", loginMember.getMemberId());
	 * return "main"; }else{ return "/member/login"; } }
	 */
	// 회원가입
	@GetMapping("/member/join")
	public String joinForm(MemberDTO memberDTO) {
		return "/member/join";
	}

	@PostMapping("/member/join")
	public String join(@Valid MemberDTO memberDTO, BindingResult bindingResult) { // @valid : 필드의 유효성 검사
		if (bindingResult.hasErrors()) {
			// 에러가 있으면 회원가입페이지에 머무름
			return "/member/join";
		}
		memberService.save(memberDTO);
		return "redirect:/";
	}

	// 로그아웃
	@GetMapping("/member/logout")
	public String logout() {
		return "redirect:/";
	}

	// 회원 목록
	@GetMapping("/member/list")
	public String list(Model model) {
		List<MemberDTO> memberDTOList = memberService.findAll();
		model.addAttribute("memberList", memberDTOList);
		return "/member/list";
	}

	// 회원 상세보기
	@GetMapping("/member/{id}")
	public String detail(@PathVariable Integer id, Model model) {
		MemberDTO memberDTO = memberService.findById(id);
		model.addAttribute("member", memberDTO);
		return "/member/detail";
	}

	// 회원 삭제
	@GetMapping("/member/delete/{id}")
	public String delete(@PathVariable Integer id) {
		memberService.deleteById(id);
		return "redirect:/member/list";
	}

	// 회원 수정
	// @AuthenticationPrincipal - 회원을 인가하는 클래스
	@GetMapping("/member/update")
	public String updateForm(@AuthenticationPrincipal SecurityUser principal, Model model) {
		MemberDTO memberDTO = memberService.findByMemberId(principal);
		model.addAttribute("member", memberDTO);
		return "/member/update";
	}

	@PostMapping("/member/update")
	public String update(@ModelAttribute MemberDTO memberDTO) {
		memberService.update(memberDTO);
		return "redirect:/member/" + memberDTO.getId();
	}
}