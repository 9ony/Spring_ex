package com.my.multiweb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.model.UserVO;
import com.user.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UserController {
	
	@Resource(name="userService")
	private UserService userService;
	
	//@RequestMapping(value="/join",method=RequestMethod.GET) 준거랑 똑같음
	@GetMapping("/join")
	public String joinForm() {
		return "/member/join";
	}
	@GetMapping("/modify")
	public String modify() {
		return "/member/modify";
	}
	@PostMapping("/join")
	public String joinEnd(Model m, @ModelAttribute("user") UserVO user) {
		log.info("join === user :"+user);
		if(user.getName()==null||user.getUserid()==null||user.getPwd()==null||
				user.getName().trim().isEmpty()||user.getUserid().trim().isEmpty()
				||user.getPwd().trim().isEmpty()) {
			return "redirect:join";
		}
		
		int n=userService.createUser(user);
		String str=(n>0)?"회원가입 완료":"가입 실패";
		String loc=(n>0)?"admin/userList":"javascript:history.back()";
		
		m.addAttribute("message",str);
		m.addAttribute("loc",loc);
		return "msg";
	}//----------------
	
	/* 스프링에서 JSON데이터를 생성해야 하는 경우
	 * 
	 * [1] pom.xml에 아래 라이브러리를 등록해야 한다.
	 * 
	 * <dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.9.8</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml -->
		<dependency>
			<groupId>com.fasterxml.jackson.dataformat</groupId>
			<artifactId>jackson-dataformat-yaml</artifactId>
			<version>2.9.8</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.9.8</version>
		</dependency>
	 * 
	 * [2] @ResponseBody 어노테이션을 붙여주고
	 * 반환되는 자료유형을 Map 또는 VO유형으로 하면 위 라이브러리가
	 * 알아서 자동으로 json형태로 변환해준다.
	 * [3] 응답유형이 json이라면
	 * produces="application/json" 을 기술하자.
	 * */

	//아이디 중복체크 ajax처리
	//@ResponseBody 페이지(jsp)를 반환하는게아니라 응답 데이터만 보내준다.
	//그리고 GetMapping안에 produces 에 응답해주는 데이터타입을 기술한다.
	@GetMapping(value="/idcheck", produces ="application/json")
	public @ResponseBody Map<String, String> idCheck(@RequestParam("userid") String userid){
		log.info("userid==="+userid);
		boolean isUse=userService.idCheck(userid);
		String reuslt=(isUse)?"ok":"no";
		
		Map<String,String> map = new HashMap<>();
		map.put("result", reuslt);
		
		return map;
	}
	@PostMapping(value="/admin/getUser", produces = "application/json")
	@ResponseBody
	public UserVO getUser(@RequestParam("idx") int idx){
		log.info("getUser idx====="+idx);
		UserVO user = userService.getUser(idx);
		log.info("getUser user ===>"+user);
		return user;
	}
	
	@GetMapping("/admin/userList")
	public String userList(Model m) {
		List<UserVO> userArr=userService.listUser(null);
		
		m.addAttribute("userArr",userArr);
		return "/member/list";
	}
	@PostMapping("/admin/userEdit")
	public String updateUser(Model m, @ModelAttribute("user") UserVO user) {
		log.info("userEdit Modelattr = >"+ user);
		if(user.getName()==null||user.getPwd()==null||
				user.getName().trim().isEmpty()||user.getPwd().trim().isEmpty()) {
			return "redirect:userList";
		}
		int n = userService.updateUser(user);
		
		String str = (n>0)?"수정완료":"수정실패";
		String loc = "userList";
		
		m.addAttribute("message",str);
		m.addAttribute("loc",loc);
		return "msg";
	}
	
	
	@PostMapping("/admin/userDel")
	public String userDelete(@RequestParam(defaultValue="0") int didx) {
	//파라미터에 변수명은 html에 id와 같아야 받아온다.
		log.info("userDel idx===>"+didx);
		if(didx==0) {
			return "redirect:userList";
		}
		int n = userService.deleteUser(didx);
		log.info("n: "+n);
		return "redirect:userList";
	}
}
