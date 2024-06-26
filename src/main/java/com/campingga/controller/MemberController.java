package com.campingga.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingga.domain.MemberVO;
import com.campingga.domain.PasswordChangeDTO;
import com.campingga.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/member")
public class MemberController {

	@Autowired // 하나 일 경우 생략가능.
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder encoder;

	// 회원가입 페이지 이동
	@GetMapping("/join")
	public void loginGET() {

		log.info("회원가입 페이지 진입");

	}

	// 회원가입
	@PostMapping("/join")
	public String joinPOST(MemberVO member) throws Exception {

		log.info("join 진입");

		// 회원가입 서비스 실행
		memberService.memberJoin(member);

		log.info("join Service 성공");

		return "redirect:/main";

	}

	@PostMapping("/memberIdChk")
    @ResponseBody
    public String memberIdChkPOST(@RequestParam("mem_id") String mem_id) throws Exception {
        log.info("memberIdChk() 진입");
        log.info("mem_id = " + mem_id);

        int result = memberService.idCheck(mem_id);

        log.info("결과값 = " + result);

        if (result != 0) {
            return "fail"; // 중복 아이디가 존재
        } else {
            return "success"; // 중복 아이디 x
        }
    }

	// 로그인 페이지 이동
	@GetMapping("/login")
	public void joinGET(String error, String logout, Model model) {
	  log.info("로그인 페이지 진입");
    log.info("error: "+error);
    log.info("logout: "+logout);
    
    if(error != null) {
      model.addAttribute("error","사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.");
    }
    if(logout != null) {
      model.addAttribute("logout","로그아웃 되었습니다.");
    }
		//log.info("로그인 페이지 진입");
		
	}

	/* 비동기방식 로그아웃 메서드 */
	@PostMapping("/logout.do")
	@ResponseBody
	//public void logoutPOST(HttpServletRequest request) throws Exception {
	public void logoutPOST(HttpSession session) throws Exception {

		log.info("비동기 로그아웃 메서드 진입");
		/*
		HttpSession session = request.getSession();
		*/
		session.invalidate();
		SecurityContextHolder.clearContext();
	}

	// 회원정보 관리 페이지 이동
	@GetMapping("/memberUpdate")
	public String memberUpdateGet() {
		return "/member/memberUpdate";
	}

	// 회원 비밀번호 변경 페이지 이동
	@GetMapping("/changePassword")
	public String changePasswordGET(Model model) {
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();   
    String mem_id = auth.getName();
	  
	  model.addAttribute("mem_id", mem_id);
		return "/member/changePassword";
		
	}

	@PostMapping("/changePassword")
	@ResponseBody
	public String changePassword(@ModelAttribute PasswordChangeDTO passwordData) {
	    log.info("=================================="+passwordData);
	    
	    String mem_id = passwordData.getMem_id();
	    String storedPassword = memberService.read(mem_id).getPwd();
	    String oldPassword = passwordData.getOldPassword();
	    String newPassword = passwordData.getNewPassword();

	    if (encoder.matches(oldPassword, storedPassword)) {
	        memberService.updatePassword(mem_id, newPassword);
	        return "success";
	    } else {
	        return "fail";
	    }
	}

	
	/* 마이 페이지 이동 */
	@GetMapping("/memberPage")
	public void memberMainGET() throws Exception {

		log.info("마이 페이지 이동");

	}

	/* 장바구니 페이지 이동 */
	@GetMapping("/toCartList")
	public String toCartList() {
		// /cart/list로 리다이렉트
		return "redirect:/cart/list";
	}

	/* 결제내역 페이지 이동 */
	@GetMapping("/toOrderList")
	public String toOrderList() {
		// /order/orderList로 리다이렉트
		return "redirect:/order/orderList";
	}

	// 회원상세 정보 페이지 이동
	@GetMapping("/memberDetailPage")
	public String memberDetailPage(Model model, HttpSession session) {
		// 세션에서 현재 로그인된 회원 정보 가져오기
		/*MemberVO member = (MemberVO) session.getAttribute("member");
		if (member == null) {
			// 회원 정보가 없으면 로그인 페이지로 리다이렉트
			return "redirect:/member/login";
		}*/
	  
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();   
    String userId = auth.getName();
    MemberVO member = memberService.read(userId);
    log.info(member);
	  
		// 회원 정보를 모델에 추가하여 뷰로 전달
		model.addAttribute("member", member);

		// 회원 정보 수정 페이지로 이동
		return "/member/memberDetailPage";
	}

	@PostMapping("/updateMemberInfo")
	@ResponseBody
	public String updateMemberInfo(@ModelAttribute MemberVO member, HttpSession session) {
	    try {
	        System.out.println("Received member: " + member); // 로그 추가
	        memberService.updateMemberInfo(member); // 회원 정보 업데이트 서비스 호출
	        session.setAttribute("member", member); // 세션에 최신 정보 반영
	        return "success"; // 업데이트 성공 시 success 반환
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "fail"; // 업데이트 실패 시 fail 반환
	    }
	}



}
