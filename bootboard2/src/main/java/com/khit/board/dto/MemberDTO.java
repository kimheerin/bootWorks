package com.khit.board.dto;

import com.khit.board.entity.Member;
import com.khit.board.entity.Role;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberDTO {
   
   private Integer id;
   private String memberId;
   private String password;
   private String name;
   private Role role;
   
   //entity -> dto 로 변환
   public static MemberDTO toSaveDTO(Member member) {
      MemberDTO memberDTO = MemberDTO.builder()
                     .id(member.getId())
                     .memberId(member.getMemberId())
                     .password(member.getPassword())
                     .name(member.getName())
                     .role(member.getRole())
                     .build();
      return memberDTO;
   }
}