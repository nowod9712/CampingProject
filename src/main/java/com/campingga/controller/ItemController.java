package com.campingga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campingga.domain.Criteria;
import com.campingga.domain.ItemVO;
import com.campingga.domain.PageDTO;
import com.campingga.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	//페이징으로 아이템 출력(기본값 1페이지)
	@GetMapping("/list")
	public String showList(@ModelAttribute("cri") Criteria cri, Model model){
		//검색기준으로 상품 리스트 받아옴
		List<ItemVO> itemList = itemService.getListWithPaging(cri);
		//리스트 전달
		model.addAttribute("itemList", itemList);
		//페이지네이션 정보 전달
		model.addAttribute("pageMaker", new PageDTO(cri,itemService.getTotalCount(cri)));
		//listTest.jsp로 이동(기존 list.jsp로 합치면 수정할 예정)
		return "listTest";
	}
}
