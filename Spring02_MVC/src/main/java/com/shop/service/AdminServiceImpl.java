package com.shop.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.multi.mapper.ProductMapper;
import com.shop.domain.CategoryVO;
import com.shop.domain.ProductVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Inject
	private ProductMapper prodMapper;
	
	@Override
	public List<CategoryVO> getUpcategory() {
		return prodMapper.getUpCategory();
	}

	@Override
	public List<CategoryVO> getDowncategory(int upCg_code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int categoryAdd(CategoryVO cvo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int categoryDelete(int cg_code) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int productInsert(ProductVO prod) {
		return prodMapper.productInsert(prod);
	}

	@Override
	public List<ProductVO> productList() {
		return prodMapper.productList();
	}

	@Override
	public ProductVO getProduct(int pnum) {
		return prodMapper.getProduct(pnum);
	}

	@Override
	public int productUpdate(ProductVO prod) {
		return prodMapper.productUpdate(prod);
	}

	@Override
	public int productDelete(int pnum) {
		return prodMapper.productDelete(pnum);
	}

}
