package com.khit.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.khit.board.dto.MemberDTO;
import com.khit.board.entity.Member;
import com.khit.board.exception.BootBoardException;
import com.khit.board.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

//@AllArgsConstructor		//final 필요 X
@RequiredArgsConstructor	//생성자 주입 방식 - final 필요
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;

	public void save(MemberDTO memberDTO) {
		//db 안으로 저장(entity 저장)
		//dto -> entity 변환 메서드 필요
		Member member = Member.toSaveEntity(memberDTO);
		
		memberRepository.save(member);
	}

	public List<MemberDTO> findAll() {
		//db에서 엔티티를 꺼내와서
		List<Member> memberList = memberRepository.findAll();
		//(변환 메서드 필요)
		//member를 담을 빈 dto 리스트를 생성
		List<MemberDTO> memberDTOList = new ArrayList<>();
		
		for(Member member : memberList) {	//memberList를 반복하며
			MemberDTO memberDTO = MemberDTO.toSaveDTO(member);
			memberDTOList.add(memberDTO);
		}
		//dtoList로 컨트롤러에 전송
		return memberDTOList;
	}

	public MemberDTO findById(Long id) {
		//findById(id).get()
		//Id가 없을 때 오류 처리 - "url을 찾을 수 없습니다"
		Optional<Member> member = memberRepository.findById(id);
		if(member.isPresent()) {	//id가 있으면
			//entity -> dto 변환
			MemberDTO memberDTO = MemberDTO.toSaveDTO(member.get());
			return memberDTO;
		}else {	//없으면
			throw new BootBoardException("사용자가 존재하지 않습니다.");
		}
		
	}

	public void deleteById(Long id) {
		//삭제 - deleteById()
	}

	public MemberDTO login(MemberDTO memberDTO) {
		//1. 이메일로 회원 조회(이메일과 비밀번호 비교)
		Optional<Member> memberEmail =
				memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
		if(memberEmail.isPresent()) {
			//조회 결과 존재 - 1건 가져옴
			Member member = memberEmail.get();
			//비밀번호 일치
			if(member.getMemberPassword().equals(memberDTO.getMemberPassword())) {
				//entitu -> dto 변환
				MemberDTO dto = MemberDTO.toSaveDTO(member); 
				return dto;
			} else {
				//비밀번호 불일치
				return null;
			}
		}else {
			return null;
		}
	}

	public MemberDTO findByMemberEmail(String email) {
		//db에서 이메일로 검색한 회원 객체를 가져와
		Member member = memberRepository.findByMemberEmail(email).get();
		//회원 객체(엔티티)를 dto로 변환
		MemberDTO memberDTO = MemberDTO.toSaveDTO(member);
		return memberDTO;
	}
	
	public void update(MemberDTO memberDTO) {
		//가입 시 id 미존재, 수정 시엔 id 존재
		Member member = Member.toUpdateEntity(memberDTO);
		//id가 있는 엔티티의 메서드 필요
		memberRepository.save(member);
	}

	public String checkEmail(String memberEmail) {
		//db에 있는 이메일을 조회하여 없으면 OK 있으면 NO
		Optional<Member> findMember = memberRepository.findByMemberEmail(memberEmail);
		if(findMember.isEmpty()) {	//db에 회원이 없으면 가입해도 되는("OK") 문자
			return "OK";
		}else {	//
			return "NO";
		}
	}
}
