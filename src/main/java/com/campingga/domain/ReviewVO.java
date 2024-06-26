 package com.campingga.domain;
 import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

 @Getter
 @Setter
 @ToString
public class ReviewVO {
  private int rownum; //리뷰번호(출력)
	private int rev_no; //리뷰번호(key)
	private int item_no; //아이템번호
	private String writer; //작성자
	private String writerID;//작성자 아이디 혹은 guest
	private String content; //내용
	private int score; //별점(1~5)
	private Date regdate; //작성일
}
