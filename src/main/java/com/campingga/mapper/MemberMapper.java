package com.campingga.mapper;

import com.campingga.domain.MemberVO;

public interface MemberMapper {
<<<<<<< Updated upstream
	//회원가입
	public void memberJoin(MemberVO member);
=======
	// 회원가입
	public void memberJoin(MemberVO member);

	// 아이디 중복 검사
	public int idCheck(String mem_id);
>>>>>>> Stashed changes
}