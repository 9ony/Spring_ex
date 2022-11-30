package com.shop.model;

import java.sql.Date;

import lombok.Data;

@Data
public class CartVO {
	private int cartnum;//장바구니번호
	private int idx_fk;//회원번호 조인해서 회원정보
	private int pnum_fk;//상품번호 조인해서 상품정보
	private int oqty;//수량
	private Date indate;
	
	//장바구니 상품정보 조인한 정보
	private String pname;
	private String pimage1;
	private int price;
	private int saleprice;
	private int point;
	
	private int totalPrice;//saleprice*oqty => 개별상품 총금액
	private int totalPoint;//point*oqty => 개별상품 총포인트
	
	private int cartTotalPrice;//장바구니에 담은 모든 상품의 총액
	private int cartTotalPoint;//
	//회원정보
}
