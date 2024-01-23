package com.khit.board.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.khit.board.entity.Member;

public class SecurityUser extends User {
	private static final long serialVersionUID = 1L;
	
	private Member member;

	public SecurityUser(Member member) {
		//암호화가 아닐 때, "{noop}" + member.getPassword() 사용
		super(member.getMemberId(), member.getPassword(),
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

}
