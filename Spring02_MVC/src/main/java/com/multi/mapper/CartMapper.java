package com.multi.mapper;

import java.util.List;

import com.shop.domain.CartVO;

public interface CartMapper {
	
	Integer selectCartNum(CartVO cvo); // pnum, userid로 장바구니번호(cnum) 가져오기
	
	int updateCartQty(CartVO cvo);
	int addCart(CartVO cvo);
	
	List<CartVO> selectCartView(String userid);
	CartVO getCartTotal(String userid);
	
	int delCart(int cartNum);
	int editCart(CartVO cvo);
	int delCartAll(String userid);
}
