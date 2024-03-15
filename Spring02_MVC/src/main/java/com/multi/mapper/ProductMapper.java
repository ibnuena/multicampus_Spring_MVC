package com.multi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shop.domain.CategoryVO;
import com.shop.domain.ProductVO;

@Mapper
public interface ProductMapper {
	
	// 상위 카테고리 목록 가져오기
	List<CategoryVO> getUpCategory();
	
	// 하위 카테고리 목록 가져오기
	List<CategoryVO> getDownCategory(int upCg_code);
	
	// 상품정보 등록
	int productInsert(ProductVO prod);
	
	// 상품 목록
	List<ProductVO> productList();
	
	// 삭제
	int productDelete(int pnum);

	ProductVO getProduct(int pnum);
	
	// 수정
	int productUpdate(ProductVO prod);

	List<ProductVO> selectByPspec(String pspec);
}
