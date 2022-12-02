package com.shop.model;

import lombok.Data;

@Data
public class ReviewVO {
	private int num;
	private String userid;
	private int pnum_fk;
	private String content;
	private int score;
	private String filename;
	private java.util.Date wdate;
}
