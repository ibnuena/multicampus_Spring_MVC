package com.shop.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.util.CommonUtil;
import com.shop.domain.CartVO;
import com.shop.service.ShopService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user")
public class CartController {

	@Inject
	private ShopService shopService;
	
	@Inject
	private CommonUtil util;
	
	@PostMapping("/cartAdd") // ��ǰ��ȣ�� ������ �Ķ���ͷ� ����
	public String addCart(Model m, CartVO cvo) {
		log.info("cvo: " + cvo);
		
		String userid = "hong123";
		cvo.setUserid(userid);
		// ��ٱ��Ͽ� �߰�
		int n = shopService.addCart(cvo); // insert or update
		
		// Ư�� ȸ���� ��ٱ��� ��� ��������
//		List<CartVO> cartArr = shopService.selectCartView(userid);
//		log.info("cartArr : " + cartArr);
//		m.addAttribute("cartArr", cartArr);
//		return "shop/cartList"; -> forward ���
		/* forward ������� �̵��ϸ� ������ ���ΰ�ħ�� ��� ��ǰ ������ �߰���
		 * redirect ������� �̵�
		 * **/
		return "redirect:cartList";
	}
	
	@GetMapping("/cartList")
	public String cartList(Model m) {
		String userid = "hong123";
		
		// Ư�� ȸ���� ��ٱ��� ��� ��������
		List<CartVO> cartArr = shopService.selectCartView(userid);
//		log.info("cartArr : " + cartArr);
		
		// ��ٱ��� �Ѿ�, ������Ʈ ��������
		CartVO cvo =  shopService.getCartTotal(userid);
		
		m.addAttribute("cartArr", cartArr);
		m.addAttribute("cartSum", cvo);
		
		return "shop/cartList";
	}
	
	@PostMapping("/cartDel")
	public String cartDelete(@RequestParam(defaultValue="0") int cnum) {
		if(cnum==0) {
			return "redirect:cartList";
		}
		
		int n = shopService.delCart(cnum);

		return "redirect:cartList";
	}
	
	@GetMapping("/cartDelAll")
	public String cartDeleteAll() {
		String userid = "hong123";
		CartVO cartVo = new CartVO();
		cartVo.setUserid(userid);
		int n = shopService.delCartAll(cartVo);
		return "redirect:cartList";
	}
	
	@PostMapping("/cartEdit")
	public String cartEdit(CartVO vo) {
		log.info("vo : " + vo);
		
		if(vo.getCnum()==0)
			return "redirect:cartList";
		
		int n = shopService.editCart(vo);
		
		return "redirect:cartList";
	}
	
	/*�������� ���� ó�� ���
	 * [1] @ExceptionHandler�� �̿��ϴ� ���
	 * [2] @ControllerAdvice�� �̿��ϴ� ���
	 * [3] @ResponseStatus �� �̿��ؼ� HTTP�����ڵ� ó���ϴ� ���
	 * *//*
	@ExceptionHandler(NumberFormatException.class)
	public String exceptionHandler(Exception ex, Model m) {
		String msg = ex.getMessage();
		
		return util.addMsgBack(m, msg);
	}*/
}
