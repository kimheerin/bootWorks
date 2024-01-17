package com.khit.study.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.khit.study.entity.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class QueryMethodTest {

   @Autowired
   private BoardRepository boardRepository;
   
//    //테스트 데이터 생성 (200개)
//   @BeforeEach
//   public void dataCreate() {
//      for(int i=1; i<=200; i++) {
//         Timestamp now = new Timestamp(System.currentTimeMillis());
//         
//         Board board = new Board();
//         board.setTitle("테스트 제목" + i);
//         board.setContent("테스트 내용" + i);
//         board.setWriter("테스터");
//         board.setCreatedDate(now);
//         
//         boardRepository.save(board);
//      }
//   }
   
//   @Test
//   public void testFindByTitle() { 
//      //BoardRepository에 선언해준 FindByTitle호출
//      List<Board> boardList = boardRepository.findByTitle("테스트 제목 100");
//      
//      for(Board board : boardList) {
//         log.info(board.toString());
//      }
//   }
//   
//   @Test
//   public void testFindByTitleContaining() { 
//      //BoardRepository에 선언해준 FindByTitle호출
//      List<Board> boardList = boardRepository.findByTitleContaining("10");
//      
//      for(Board board : boardList) {
//         log.info(board.toString());
//      }
//   }
//   
//   @Test
//   public void testFindByTitleContainingOrContentContaining() {
//      List<Board> boardList = boardRepository.findByTitleContainingOrContentContaining("10", "17");
//      
//      boardList.forEach(board -> log.info(board.toString()));
//   }
   
/* -------------------------------------------
    정렬해서 조회하기! (내림차순 Desc)
-------------------------------------------------*/ 
   
   // 제목에 특정 단어가 포함된 글 목록을 내림차순으로 조회 
//   
//   @Test
//   public void testFindByTitleContainingOrderByIdDesc() {
//      List<Board> boardList = boardRepository.findByTitleContainingOrderByIdDesc("10");
//      
//      for(Board board : boardList) {
//         log.info(board.toString());
//      }
//   }
	
   // 제목에 특정 단어가 포함된 글 목록을 페이지 처리하여 조회
	@Test
	public void testFindByTitleContaining() {
		// 0 -> 1페이지
		//Pageable paging = PageRequest.of(0, 5);
		Pageable paging = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
		log.info("page: " + paging.getPageNumber());
		log.info("size: " + paging.getPageSize());
				
		List<Board> boardList =
				boardRepository.findByTitleContaining("제목" + paging);
		
		boardList.forEach(board -> log.info(board.toString()));
	}
}
