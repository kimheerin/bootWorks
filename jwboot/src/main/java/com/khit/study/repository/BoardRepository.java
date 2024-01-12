package com.khit.study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khit.study.entity.Board;

//JPARepository를 상속해야함
public interface BoardRepository extends JpaRepository <Board, Integer>{
   //쿼리 메서드 실습

	//1. 제목 검색
   List<Board> findByTitle(String searchKeyword);
   
   //2. 제목 포함 검색
   List<Board> findByTitleContaining(String searchKeyword);
   
   //제목 or 내용
   List<Board> findByTitleContainingOrContentContaining(String title,
            String content);
}