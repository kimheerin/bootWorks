package com.khit.board.service;

import com.khit.board.config.SecurityUser;
import com.khit.board.dto.MemberDTO;
import com.khit.board.entity.Member;
import com.khit.board.entity.Role;
import com.khit.board.exception.BootBoardException;
import com.khit.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder pwEncoder;

	public Member login(Member member) {
		// db에서 아이디 조회
		Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
		if (findMember.isPresent()) {
			return findMember.get();
		} else {
			return null;
		}
	}

	// 회원가입
	public void save(MemberDTO memberDTO) {
		// 1. 비밀번호 암호화
		String encPw = pwEncoder.encode(memberDTO.getPassword());
		memberDTO.setPassword(encPw);
		// 2. 권한설정
		memberDTO.setRole(Role.MEMBER);
		// 변환 매서드
		Member member = Member.toSaveEntity(memberDTO);
		memberRepository.save(member);
	}

	public List<MemberDTO> findAll() {
		// db에서 memberList를 가져옴
		List<Member> memberList = memberRepository.findAll();
		// 비어있는 memberDTOList를 생성
		List<MemberDTO> memberDTOList = new ArrayList<>();
		// memberDTOList에 memberDTO를 저장
		for (Member member : memberList) {
			MemberDTO memberDTO = MemberDTO.toSaveDTO(member);
			memberDTOList.add(memberDTO);
		}
		return memberDTOList;
	}

	public MemberDTO findById(Integer id) {
		// db에서 member를 꺼내옴
		Optional<Member> findMember = memberRepository.findById(id);
		if (findMember.isPresent()) {
			// entity -> dto변환
			MemberDTO memberDTO = MemberDTO.toSaveDTO(findMember.get());
			return memberDTO;
		} else {
			throw new BootBoardException("NOT FOUND PAGE!");
		}
	}

	public void deleteById(Integer id) {
		memberRepository.deleteById(id);
	}

	public MemberDTO findByMemberId(SecurityUser principal) {
		Optional<Member> member = memberRepository.findByMemberId(principal.getUsername());
		// 반환
		MemberDTO memberDTO = MemberDTO.toSaveDTO(member.get());
		return memberDTO;
	}

	// 회원수정
	public void update(MemberDTO memberDTO) {
		// 암호화, 권한 설정
		String encPw = pwEncoder.encode(memberDTO.getPassword());
		memberDTO.setPassword(encPw);
		memberDTO.setRole(Role.MEMBER);
		// 변환
		Member member = Member.toSaveEntity(memberDTO);
		memberRepository.save(member);
	}
}