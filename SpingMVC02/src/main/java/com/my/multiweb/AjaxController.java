package com.my.multiweb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.model.MemoVO;
import com.user.model.UserVO;

import lombok.extern.log4j.Log4j;
//AJAX 테스트
@Controller
@Log4j
public class AjaxController {
	@RequestMapping(value="/ajaxView",method=RequestMethod.GET)
	public String ajaxForm() {
		return "ajax/ajaxView";
	}
	@GetMapping(value="/ajaxVO", produces = "application/json")
	@ResponseBody
	public UserVO ajaxVO(int num, String name) {
		log.info("num: "+num+", name: "+name);
		
		UserVO vo = new UserVO(num,name,"test","a","b","c","d","e","f","g",null,1000,0,"일반회원");
		return vo;
	}
	
	@GetMapping(value="/ajaxList", produces = "application/json")
	@ResponseBody
	public List<MemoVO> ajaxList(int idx,String name){
		log.info("idx : "+idx+", name: "+name);
		MemoVO vo=new MemoVO();
		vo.setIdx(idx);
		vo.setName(name);
		vo.setMsg("안녕하세요");
		vo.setWdate(null);
		MemoVO vo1=new MemoVO();
		vo1.setIdx(idx+1);
		vo1.setName(name);
		vo1.setMsg("안녕하세요1");
		vo1.setWdate(null);
		MemoVO vo2=new MemoVO();
		vo2.setIdx(idx+2);
		vo2.setName(name);
		vo2.setMsg("안녕하세요2");
		vo2.setWdate(null);
		List<MemoVO> result = new ArrayList<>();
		result.add(vo);
		result.add(vo1);
		result.add(vo2);
		return result;
	}
}
