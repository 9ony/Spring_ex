package com.shop.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shop.mapper.CategoryMapper;
import com.shop.mapper.ProductMapper;
import com.shop.model.CategoryVO;
import com.shop.model.ProductVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Inject
	private CategoryMapper categoryMapper;
	
	@Inject
	private ProductMapper productMapper;
	
	@Override
	public List<CategoryVO> getUpcategory() {
		// TODO Auto-generated method stub
		return categoryMapper.getUpcategory();
	}

	@Override
	public List<CategoryVO> getDowncategory(String upCg_code) {
		// TODO Auto-generated method stub
		return categoryMapper.getDowncategory(upCg_code);
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
		// TODO Auto-generated method stub
		return productMapper.productInsert(prod);
	}
	
	public int productDelete(int pnum) {
		return productMapper.productDelete(pnum);
	}
	
	@Override
	public int productUpdate(ProductVO prod) {
		return productMapper.productUpdate(prod);
	}
	
	@Override
	public List<ProductVO> productList() {
		// TODO Auto-generated method stub
		return productMapper.getProducts();
	}

	@Override
	public ProductVO selectByPnum(int pnum) {
		// TODO Auto-generated method stub
		return productMapper.selectByPnum(pnum);
	}

}
