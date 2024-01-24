package com.khit.board.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "member")
@Setter
@Getter
@Entity
@Table(name = "t_board")
public class Board extends BaseEntity{
	
	@Id //필수 입력 - PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//자동 순번
	private Integer id;
	
	@Column(nullable = false) 	//NOT NULL
	private String title;
	
	@Column(nullable = false, length = 2000)	//NOT NULL, 2000자까지
	private String content;
	
	@CreationTimestamp
	private Timestamp createdDate;
	
	//Board 엔티티와 연관관계 매핑
	//다대일 매핑(다: Board)
	//fetch : 검색 및 조회 시에, EAGER : 전체 조회 LAZY : 특정 조회
	//joinColumn - 외래키 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")	//지정한 칼럼 네임 가져오기
	private Member member;
	
}
