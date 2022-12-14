package com.shop.model;

import java.sql.Date;

import lombok.Data;
//html input name과 VO의 property명 , DB table의 column명을 일치시켜주면 편리하다.
@Data
public class ProductVO {
	//property
	private String upCg_code;
	private String upCg_name;
	
	private String downCg_code;
	private String downCg_name;
	
	private int pnum;
	private String pname;
	private String pimage1;
	private String pimage2;
	private String pimage3;
	
	private int price;
	private int saleprice;
	private int point;
	private int pqty;
	
	private String pspec;
	private String pcontents;
	private String pcompany;
	private Date pindate;
	
	private int totalPrice; /*총판매가 = 상품판매가 x수량*/
	private int totalPoint; /*총포인트 = 포인트 x수량*/
	
	private String onum; //상품 주문시 사용할 주문번호
	
	//수량이정해지면 총판매가 총포인트 계산
	public void setPqty(int pqty) {
		this.pqty = pqty;
		this.totalPrice = this.saleprice * this.pqty;
		this.totalPoint = this.point * this.pqty;
	}
	/**할인율을 반환하는 메서드*/
	public int getPercent() {
		// 할인율 계산 (정가-판매가)*100/정가
		int percent=(price-saleprice)*100/price;
		return percent;
	}
	public void setPimage(int index,String pimageName) {
		if(index==0) {
			setPimage1(pimageName);
		}else if(index==1) {
			 setPimage2(pimageName);
		}else if(index==2){
			setPimage3(pimageName);
		}else {
			new Exception("잘못된 인덱스 입니다.");
		}
	}
}
