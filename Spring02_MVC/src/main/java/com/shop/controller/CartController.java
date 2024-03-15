package com.shop.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.domain.CartVO;
import com.shop.service.ShopService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user")
public class CartController {

	@Inject
	private ShopService shopService;
	
	@PostMapping("/cartAdd") // ��ǰ��ȣ�� ������ �Ķ���ͷ� ����
	public String addCart(Model m, CartVO cvo) {
		log.info("cvo: " + cvo);
		
		String userid = "hong123";
		cvo.setUserid(userid);
		// ��ٱ��Ͽ� �߰�
		int n = shopService.addCart(cvo); // insert or update
		
		// Ư�� ȸ���� ��ٱ��� ��� ��������
		List<CartVO> cartArr = shopService.selectCartView(userid);
		
		log.info("cartArr : " + cartArr);
		
		m.addAttribute("cartArr", cartArr);
		m.addAttribute("userid", userid);
		
		return "shop/cartList";
	}
	
}
