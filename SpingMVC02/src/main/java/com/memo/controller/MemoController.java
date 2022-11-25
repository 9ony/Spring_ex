package com.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.model.MemoDAO;
import com.memo.model.MemoVO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MemoController {
	//by type으로 주입 MemoDAO타입의 객체가 있으면 주입한다.
	@Autowired
	private MemoDAO memoDao;
	
	//RequsetMapping을 해줬으면 servlet-context.xml에 context:component-scan에 현재 패키지 경로추가
	@RequestMapping(value="/memo",method=RequestMethod.GET)
	public String memoForm() {
		return "memo/input";
		//"/WEB_INF/views/memo/input/"
	}
	
	
	//html input name과 VO객체의 property명이 같고
	//ModelAttribute 어노테이션을 붙여주면 파라미터값들을 자동으로 VO객체에 넣어준다.
	@RequestMapping(value="/memo",method=RequestMethod.POST)
	public String memoInsert(Model model, @ModelAttribute("memo") MemoVO memo) {
		//System.out.println("momo======"+memo);
		//Spring에서는 log를 주로사용
		//debug<info<warning<error 로 로그레벨순이있는데 개발단계에서는 info를쓰지만
		//제품 완성이 로그레벨을 warning이나 error로 바꾼다
		log.info("memo>>>"+memo);
		
		int n = memoDao.insertMemo(memo);
		
		String str=(n>0)?"등록성공":"등록 실패";
		String loc=(n>0)?"memoList":"javascript:history.back()"
;		model.addAttribute("message",str);
		model.addAttribute("loc",loc);
		return "msg";
	}
	
	@RequestMapping(value="/memoList",method=RequestMethod.GET)
	public String memoList(Model model) {
		int totalCount=memoDao.getTotalCount();
		List<MemoVO> arr=memoDao.listMemo();
		
		model.addAttribute("memoArr", arr);
		model.addAttribute("totalCount",totalCount);
		
		return "memo/list";
		//"/WEB-INF/views/memo/list.jsp"
	}
	
	@RequestMapping(value="/memoDel")
	public String memoDelete(Model model, @RequestParam(defaultValue = "0") int idx) {
		log.info("del idx info==="+idx); //defaut값은 0으로 idx가 넘어오지않으면!
		if(idx==0) {
			return "redirect:memoList";
		}
		int n = memoDao.deleteMemo(idx);
		
		String str=(n>0)?"삭제성공":"삭제실패";
		String loc=(n>0)?"memoList":"javascript:history.back()";
		model.addAttribute("message",str);
		model.addAttribute("loc",loc);
		return "msg";
		
	}
	
	@RequestMapping(value="/memoEdit")
	public String memoEdit(Model model, @RequestParam(defaultValue = "0") int idx) {
		log.info("edit idx info====="+idx);
		if(idx==0) {
			return "redirect:memoList";
		}
		MemoVO vo = memoDao.selectMemo(idx);
		
		model.addAttribute("memoVO",vo);
		return "memo/edit";
	}
	@RequestMapping(value="/memoEdit", method=RequestMethod.POST)
	public String memoEditEnd(Model model, @ModelAttribute("memo") MemoVO memo) {
		log.info("edit idx info====="+memo);
		int n = memoDao.updateMemo(memo);
		
		String str=(n>0)?"수정성공":"수정실패";
		model.addAttribute("message",str);
		model.addAttribute("loc","memoList");
		return "msg";
	}
}
