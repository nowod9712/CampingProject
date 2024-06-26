package com.campingga.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.campingga.domain.Criteria;
import com.campingga.domain.ReviewVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j 
public class ReviewMapperTests {
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Test
	public void getReviewListTest() {
	  List<ReviewVO> reviewList = reviewMapper.selectReviewList(); 
	  reviewList.forEach(review -> log.info(review));
	}
	
	@Test
	public void getReviewListWithPagingTest() {
	  Criteria cri = new Criteria(1, 5); //페이지, 페이지당 게시글 수
	  int total = reviewMapper.selectCountByItemNo(1); //리스트를 가져올 리뷰 번호
	  log.info(total);
	  List<ReviewVO> reviewList = reviewMapper.selectReviewListWithPaging(cri,1,total); //페이징, 상품번호, 총상품수
	  
	  reviewList.forEach(review -> log.info(review));
	  
	}
	
	//테스트
	@Test
	public void insertReviewTest() {
		ReviewVO vo = new ReviewVO();
		vo.setContent("내용~!");
		vo.setItem_no(1);
		vo.setWriter("user003");
		vo.setScore(4);
		
		reviewMapper.insertReview(vo);
	}
	
	//삭제 테스트
	@Test
	public void deleteReviewTest() {
	  int result = reviewMapper.deleteReview(15);
	  log.info("------------------------"+result);
	}
	
	//리뷰 한 개 조회 테스트
	@Test
	public void selectReviewTest() {
	 ReviewVO vo = reviewMapper.selectReview(17);
	 
	 log.info(vo);
	}
	
	@Test
	public void updateReviewTest() {
		ReviewVO vo = new ReviewVO();
		vo.setContent("새로 수정하는 내용입니다~!~!~!~!~!~!~");
		vo.setRev_no(6);
		vo.setScore(5);
		log.info(reviewMapper.updateReview(vo));
	}
}
