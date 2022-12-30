package com.project.space;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;
import com.project.space.domain.Mem_InfoVO;
import com.project.space.domain.NaverLoginCallbackVO;
import com.project.space.domain.NaverLoginVO;
import com.project.space.user.naverlogintest.bo.NaverLoginBO;
import com.project.space.user.service.UserService;

import lombok.extern.log4j.Log4j;
@Controller
@Log4j
public class GonTestCont {
	
	@Inject
	private NaverLoginBO naverLoginBO;
	
	@Inject
	private UserService userService;
	
	@GetMapping("/MyZimm")
	public String zimmList() {
		return "ajax/ilgon/MyZimm";
	}
	@GetMapping("/MyReviewList")
	public String myReviewList() {
		
		return "ajax/ilgon/MyReviewList";
	}
	@GetMapping("/MyModify")
	public String mymodify() {
		return "ajax/Pages/MyModify";
	}
	@GetMapping("/MyReservationCheck")
	public String myReservationCheck() {
		return "ajax/OwnerPage/MyReservationCheck";
	}
	@GetMapping("/MySpaceEdit")
	public String mySpaceEdit() {
		
		return "ajax/OwnerPage/MySpaceEdit";
	}
	@GetMapping("/MySpaceInsert")
	public String mySpaceInsert() {
		
		return "ajax/OwnerPage/MySpaceInsert";
	}
	@GetMapping("/MySpaceList")
	public String mySpaceList() {
		
		return "ajax/OwnerPage/MySpaceList";
	}
	
	/*네로아 테스트*/
	@GetMapping("/NaverLogin")
	public ModelAndView naverLogin(HttpSession session) {
		log.info("NaverLogin connect");
		 /* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        
        /* 생성한 인증 URL을 View로 전달 */
        return new ModelAndView("ajax/Login", "url", naverAuthUrl);
	}
	@GetMapping("/NaverCallback")
	public ModelAndView naverCallback(HttpSession session,@RequestParam String code, @RequestParam String state) throws Exception{
		log.info("NaverCallback connect");
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);

		String naverProfileInfo = naverLoginBO.getUserProfile(oauthToken);
		log.info("check naverCallback try");
		
		/*json문자열 gson을 이용해서 vo객체생성*/
		Gson gson = new Gson();
		//callback 요청값으로 받아온 naverProfileInfo json문자열을 VO객체로 파싱
		NaverLoginCallbackVO nlcVO = gson.fromJson(naverProfileInfo, NaverLoginCallbackVO.class);
		//log.info(nlcVO.getMessage());
		//NaverLoginCallbackVO 안에 response에 네이버 프로필정보를 nlVO 에담아줌 (굳이할필욘없음 애초에 NaverLoginVO 타입으로 받음) 
		NaverLoginVO nlVO = nlcVO.getResponse();
		log.info("네이버 로그인 API 프로필정보 ===> "+nlVO);
		
		int result = userService.idCheck(nlVO.getId());
		if(result>0) {
			Mem_InfoVO memInfoVO = userService.getUser(nlVO.getId());
			log.info("id일치"+memInfoVO.getUserid()+"////"+memInfoVO);
			session.setAttribute("loginUser", memInfoVO);
			return new ModelAndView("redirect:/");
		}else{
			//회원가입을위해 Naver Profile 정보를 넘겨준다.
			return new ModelAndView("ajax/NaverCallback","result",nlVO);
		}
		//return new ModelAndView("ajax/NaverCallback", "result", nlVO);
	}
	@GetMapping("/NaverDelete")
	public String naverDelete(HttpSession session) throws Exception{
		OAuth2AccessToken ReadSessionToken = (OAuth2AccessToken) session.getAttribute("naver_oauthToken");
		//log.info("NaverDelete // access_token ===>"+ReadSessionToken.getParameter("access_token"));
		log.info(ReadSessionToken.getAccessToken());
		String deleteTokenUrl = naverLoginBO.NaverDeleteToken(ReadSessionToken.getAccessToken());
		log.info("deleteTokenUrl====>"+deleteTokenUrl);
		URL obj = new URL(deleteTokenUrl); // 호출할 url
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");
        con.connect();
		log.info(con.getResponseCode());
		session.invalidate();
		return "redirect:/";
	}
	@PostMapping("/NaverJoin")
	public String memberInfoAdd(Model m , @ModelAttribute Mem_InfoVO vo) {
		log.info("NaverJoin post value VO===>"+vo);
		m.addAttribute("MemInfo",vo);
		m.addAttribute("flag","NAVER");
		return "ajax/ilgon/NaverJoin";
	}
	@PostMapping("/Join")
	public String joinEnd(Model m, @ModelAttribute Mem_InfoVO vo) {
		log.info("join === user :"+vo);
		if(vo.getMname()==null||vo.getUserid()==null||vo.getMpwd()==null||
				vo.getMname().trim().isEmpty()||vo.getUserid().trim().isEmpty()
				||vo.getMpwd().trim().isEmpty()) {
			return "redirect:ajax/ilgon/NaverJoin";
		}
		
		int n=userService.insertUser(vo);
		//성공하면 home 실패시 뒤로가기
		String loc=(n>0)?"redirect:/":"redirect:ajax/ilgon/NaverJoin";
		
		return loc;
	}//----------------
	
	//비동기처리 테스트 코드
	/*
	@PostMapping("/NaverJoin2")
	public String memberInfoAdd2(Model m , @ModelAttribute Mem_InfoVO vo ,RedirectAttributes r) {
		log.info("NaverJoin post value VO===>"+vo);
		r.addFlashAttribute("flag", "naver");
		r.addFlashAttribute("userid",vo.getUserid());
		r.addFlashAttribute("mname",vo.getMname());
		r.addFlashAttribute("birth",vo.getBirth());
		r.addFlashAttribute("hp",vo.getHp());
		return "redirect:/";
	}
	@GetMapping("/NaverJoinTest")
	public String naverJointest() {
		return "ajax/ilgon/NaverJoinTest";
	}
	*/
	//-------------------------
}
