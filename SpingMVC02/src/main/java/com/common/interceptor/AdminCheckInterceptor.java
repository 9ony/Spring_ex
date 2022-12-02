package com.common.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.user.model.UserVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class AdminCheckInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest req,HttpServletResponse res, Object handler) throws Exception{
		HttpSession ses=req.getSession();
		UserVO user=(UserVO)ses.getAttribute("loginUser");
		if(user.getStatus()!=9) {
			log.info("preHandler not Admin");
			String view="/WEB-INF/views/msg.jsp";
			req.setAttribute("message", "!!관리자만 가능해요!!");
			req.setAttribute("loc", req.getContextPath()+"/index");
			
			//dispatcher를 통해 forward이동
			RequestDispatcher disp=req.getRequestDispatcher(view);
			disp.forward(req, res);
			return false;
		}
		
		
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest req,HttpServletResponse res, Object handler, ModelAndView mv) 
			throws Exception
	{
		log.info("어드민체크 postHandle()");
		super.postHandle(req,res,handler,mv);
	}
	//[3] Controller를 실행하고 뷰를 실핼후에 호출되는 메서드
	@Override
	public void afterCompletion(HttpServletRequest req,HttpServletResponse res, Object handler,Exception ex) 
			throws Exception
	{
		log.info("어드민체크 afterCompletion()");
		super.afterCompletion(req, res, handler, ex);
	}
}
