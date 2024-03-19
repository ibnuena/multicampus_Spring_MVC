package com.shop.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.util.CommonUtil;
import com.shop.domain.CartVO;
import com.shop.service.ShopService;
import com.user.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user")
public class CartController {

	@Inject
	private ShopService shopService;
	
	@Inject
	private CommonUtil util;
	
	@PostMapping("/cartAdd") // 상품번호와 수량이 파라미터로 들어옴
	public String addCart(Model m, CartVO cvo, HttpSession session) {
//		log.info("cvo: " + cvo);
		
		MemberVO user = (MemberVO)session.getAttribute("loginUser");
		String userid = user.getUserid();
		
		cvo.setUserid(userid);
		// 장바구니에 추가
		int n = shopService.addCart(cvo); // insert or update
		
		// 특정 회원의 장바구니 목록 가져오기
//		List<CartVO> cartArr = shopService.selectCartView(userid);
//		log.info("cartArr : " + cartArr);
//		m.addAttribute("cartArr", cartArr);
//		return "shop/cartList"; -> forward 방식
		/* forward 방식으로 이동하면 브라우저 새로고침시 계속 상품 수량이 추가됨
		 * redirect 방식으로 이동
		 * **/
		return "redirect:cartList";
	}
	
	@GetMapping("/cartList")
	public String cartList(Model m, HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("loginUser");
		String userid = user.getUserid();
		
		// 특정 회원의 장바구니 목록 가져오기
		List<CartVO> cartArr = shopService.selectCartView(userid);
//		log.info("cartArr : " + cartArr);
		
		// 장바구니 총액, 총포인트 가져오기
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
	public String cartDeleteAll(HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("loginUser");
		String userid = user.getUserid();
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
	
	/*스프링의 예외 처리 방법
	 * [1] @ExceptionHandler를 이용하는 방법
	 * [2] @ControllerAdvice를 이용하는 방법
	 * [3] @ResponseStatus 를 이용해서 HTTP상태코드 처리하는 방법
	 * *//*
	@ExceptionHandler(NumberFormatException.class)
	public String exceptionHandler(Exception ex, Model m) {
		String msg = ex.getMessage();
		
		return util.addMsgBack(m, msg);
	}*/
}
