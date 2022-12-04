package com.common.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.user.model.UserVO;

import lombok.extern.log4j.Log4j;

/* Interceptor
 *  - 컨트롤러가 실행되기 전에 사전 처리할 일이 있으면 
 *    스프링에서는 인터셉터에서 구현한다.
 *  - 구현 방법
 *  1. 인터셉터 구현
 *     [1] HandlerInterceptor인터페이스를 상속받는 방법
 *     [2] HandlerInterceptorAdapter 추상클래스를 상속받는 방법
 *      
 *  2. 인터셉터 등록 => servlet-context.xml에서 등록하고 매핑 정보를 설정
 *  <!-- Interceptor설정 =========================================================== -->
   <interceptors>
         <interceptor>
            <mapping path="/user/**"/>
            <mapping path="/admin/**"/>
            <beans:bean class="com.common.interceptor.LoginCheckInterceptor"/>
         </interceptor>
   </interceptors>
 * */
@Log4j
public class LoginCheckInterceptor implements HandlerInterceptor{
	//[1] preHandle= 컨트롤러를 실행하기 전에 호출되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest req,HttpServletResponse res, Object handler) throws Exception{
		log.info("preHandler()");
		
		String header=req.getHeader("Ajax");//ajax요청시 헤더로 포함된 key갑이 Ajax인 값을 header에 넣는다
		String headertest=req.getHeader("X-Requested-With"); // Test
		log.info("header==>"+header);
		HttpSession ses=req.getSession();
		UserVO user=(UserVO)ses.getAttribute("loginUser");
		if(user==null) {
			if("true".equals(header)) { //header에 포함되서온 Ajax키값의 밸류가 true면
				res.sendError(999);//에러코드 999을보낸다
				return false;
			}else if("XMLHttpRequest".equals(headertest)) { //test
				//기본적으로 ajax요청은 헤더에 X-Requested-With가 포함되어 있다. 
				res.sendError(999);
				return false;
			}
			else {
				log.info("preHandler not User");
				String view="/WEB-INF/views/msg.jsp";
				req.setAttribute("message", "로그인해야 이용 가능 합니다");
				req.setAttribute("loc", req.getContextPath()+"/index");
				
				//dispatcher를 통해 forward이동
				RequestDispatcher disp=req.getRequestDispatcher(view);
				disp.forward(req, res);
				return false;
			}
		}
		return true;
	}
	//[2] postHandle= 컨트롤러를 실행한후 뷰를 실행하기 전에 호출되는 메서드
	//ModelAndView 파라미터가 하나 더들어감
	//ModelAndView = model과 view를 같이 가질수 있는 객체
	@Override
	public void postHandle(HttpServletRequest req,HttpServletResponse res, Object handler, ModelAndView mv) 
			throws Exception
	{
		log.info("로그인체크 postHandle()");
		HandlerInterceptor.super.postHandle(req,res,handler,mv);
	}
	//[3] Controller를 실행하고 뷰를 실핼후에 호출되는 메서드
	@Override
	public void afterCompletion(HttpServletRequest req,HttpServletResponse res, Object handler,Exception ex) 
			throws Exception
	{
		log.info("로그인체크 afterCompletion()");
		HandlerInterceptor.super.afterCompletion(req, res, handler, ex);
	}
}
