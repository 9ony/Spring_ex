package com.memo.model;
import java.util.*;
import lombok.*;
//import lombok.*; 임포트를 해주고
//@Data를 입력하면 웬만한 VO설정 다해줌

//어노테이션으로 setter , getter만 만들어줄수 있음
@Setter
@Getter
@ToString(includeFieldNames = true) //toString작성해줌
public class MemoVO {
	
	private int idx;
	private String name;
	private String msg;
	private Date wdate;
}
 