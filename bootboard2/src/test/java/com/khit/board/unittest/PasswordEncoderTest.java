package com.khit.board.unittest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.khit.board.entity.Member;
import com.khit.board.entity.Role;
import com.khit.board.repository.MemberRepository;

@SpringBootTest
public class PasswordEncoderTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Test
	public void testInserData() {
		//일반 회원 - 저장
		Member member = new Member();
		member.setMemberId("khit");
		member.setPassword(pwEncoder.encode("khit123"));
		member.setName("채정안");
		member.setRole(Role.MEMBER);
		
		memberRepository.save(member);
	}
}
