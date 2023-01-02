package com.project.space.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PointVO {  //포인트 충전
	private int paynum;  //충전번호
	private String userid; //회원아이디
	private int plusPoint; //충전할 포인트
	private String paykind; //충전 경로
	private Date paydate;  //충전한 날짜
	private int paystatus;  //충전상태
	
}
