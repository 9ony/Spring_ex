package com.my.multiweb;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.user.model.NotUserException;
import com.user.model.UserVO;
import com.user.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class LoginController {
	
	@Inject
	private UserService userService;
	
	//로그인
	/*
	@GetMapping("/login")
	public String login() {
		return "/member/loginModal";
	}
	*/
	
	@PostMapping("/login")
	public String findUser(HttpSession session ,UserVO user) 
			throws NotUserException
	{
		log.info("findUser param ===>"+user);
		if(user.getUserid().trim().isEmpty()) {
			return "redirect:index";
		}
		UserVO loginUser = userService.loginCheck(user.getUserid(),user.getPwd());
		log.info("findUser value ===>"+loginUser);
		if(loginUser!=null) {
			session.setAttribute("loginUser", loginUser);
		}
		return "redirect:index";
		
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); //세션정보 삭제!
		return "redirect:index";
	}
	
	//예외처리하는 메서드앞에 @ExceptionHandler를 붙이고 구체적인 예외 클래스를 지정한다
	@ExceptionHandler(NotUserException.class)
	public String exceptionHandler(Exception ex) {
		log.error(ex);
		return "login/errorAlert";
	}
	
}
