package com.memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemoController {
	
	//RequsetMapping을 해줬으면 servlet-context.xml에 context:component-scan에 현재 패키지 경로추가
	@RequestMapping(value="/memo",method=RequestMethod.GET)
	public String memoForm() {
		return "memo/input";
	}
}
