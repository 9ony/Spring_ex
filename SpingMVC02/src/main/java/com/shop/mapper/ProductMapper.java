package com.shop.mapper;

import java.util.List;

import com.shop.model.CategoryVO;
import com.shop.model.ProductVO;

public interface ProductMapper {
	int productInsert(ProductVO vo);
	int productUpdate(ProductVO vo);
	int productDelete(int pnum);
	
	List<ProductVO> getProducts();
	
	List<ProductVO> selectByPspec(String pspec);
	List<ProductVO> selectByCategory(CategoryVO cvo);
	
	ProductVO selectByPnum(int pnum);
	
	
}
