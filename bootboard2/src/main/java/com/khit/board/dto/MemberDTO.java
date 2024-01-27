package com.khit.board.dto;

import java.sql.Timestamp;

import com.khit.board.entity.Member;
import com.khit.board.entity.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberDTO {

	private Integer id;
	
	// 아이디는 4~20자 입력
	@Size(min = 4, max = 20)
	@NotEmpty(message = "ID is required")
	private String memberId;
	
	@NotEmpty(message = "Password is required")
	private String password;
	
	@NotEmpty(message = "Name is required")
	private String name;
	
	private Role role;

	private Timestamp createdDate;
	
	private Timestamp UpdateDate;
	
	// entity -> dto 로 변환
	public static MemberDTO toSaveDTO(Member member) {
		MemberDTO memberDTO = MemberDTO.builder()
				.id(member.getId())
				.memberId(member.getMemberId())
				.password(member.getPassword())
				.name(member.getName())
				.role(member.getRole())
				.createdDate(member.getCreatedDate())
				.UpdateDate(member.getUpdatedDate())
				.build();
		return memberDTO;
	}
}