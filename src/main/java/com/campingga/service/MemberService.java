package com.campingga.service;

import java.util.List;

import com.campingga.domain.MemberPagingVO;
import com.campingga.domain.MemberVO;

public interface MemberService {

	//회원가입
	public void memberJoin(MemberVO member) throws Exception;
	
	//아이디 중복 검사
	public int idCheck(String mem_id);
	
	//로그인 
    public MemberVO memberLogin(MemberVO member) throws Exception;

    // 회원 총 
    public int countMember();

    // 페이징 처리 게시글 조회
    public List<MemberVO> selectMember(MemberPagingVO vo);
	
}
