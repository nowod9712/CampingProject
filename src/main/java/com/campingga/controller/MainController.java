package com.campingga.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.campingga.domain.ItemVO;
import com.campingga.service.ItemService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MainController {
	
		@Autowired
		private ItemService itemService;
	
		//메인 페이지 이동
		@GetMapping("/main")
		public void mainPageGET(Model model) {
			/*
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			//사용자가 anonymousUser(로그인 하지 않은 사용자)인지 확인
			if (auth != null && auth.isAuthenticated()
					&& !"anonymousUser".equals(auth.getPrincipal())) {
				
				model.addAttribute("mem_id", auth.getName());
				
				//관리자 권한을 가지고 있으면
				if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
					model.addAttribute("isAdmin", true);
				}
				else {
					model.addAttribute("isAdmin", false);
				}
				
			}
			*/
			List<ItemVO> newItem = itemService.getNewItems();
			
			log.info("메인 페이지 진입");
			
			
			model.addAttribute("newItem", newItem);
			
		}
		// 업로드 이미지 출력
		@GetMapping("/display")
		public ResponseEntity<byte[]> getImage(String fileName){
			
			//"c:\\upload\\"을 작성한 이유는 특수 문자'\'을 인식할 수 있도록
			File file = new File("c:\\upload\\" + fileName);
			
			//뷰로 반환할 ResponseEntity 객체의 주소를 저장할 참조 변수를 선언
			ResponseEntity<byte[]> result = null;
			
			try {
				
				HttpHeaders header = new HttpHeaders();
				
				header.add("Content-type", Files.probeContentType(file.toPath()));
				
				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
				
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
			
		}
}
