package com.khit.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khit.board.entity.Member;

//JPARepository 상속 필요(@Repository 생략 가능)
public interface MemberRepository extends JpaRepository<Member, Long> {
	//제공된 메서드 - save(), findAll(), findById(), deleteById()
	//쿼리 메서드(쿼드명: 메서드) - Optional(null 체크 클래스)
	
		//select * from tbl_member where member_email = ?;
		Optional<Member> findByMemberEmail(String memberEmail);
	
}
