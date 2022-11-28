package com.shop.model;

import lombok.Data;

@Data
public class CategoryVO {
	
	private String upCg_code;//상위카테고리코드
	private String upCg_name;//상위카테고리명
	
	private String downCg_code;//하위카테고리코드
	private String downCg_name;//하위카테고리명
}
