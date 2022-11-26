package com.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
	private int idx;
	private String name;
	private String userid;
	private String pwd;
	private String hp1,hp2,hp3;
	private String post;
	private String addr1,addr2;
	private java.sql.Date indate;
	private int mileage;
	private int status; //0: 일반 1:정지 2:탈퇴
	private String statusStr;

	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	public String getAllAddr() {
		return "["+post+"] "+addr1+" "+addr2;
	}
}
