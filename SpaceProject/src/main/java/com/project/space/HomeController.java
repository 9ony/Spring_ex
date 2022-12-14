package com.project.space;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.space.domain.Space_InfoVO;
import com.project.space.spaceinfo.service.SpaceInfoService;

import lombok.extern.log4j.Log4j;


/**
 * Handles requests for the application home page.
 */
@Controller
@Log4j
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	SpaceInfoService spaceinfoservice;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test1(Model model) {
		return "NewFile";
	}
	// MainHome
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Mainhome(Model model, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage ) {
		logger.info("connected Home");
		Map<String, String> pagingMap = new HashMap<String, String>();
		int pageSize = 8;
		int pagingNumber = currentPage;

		pagingMap.put("pagingSize", Integer.toString(pageSize));
		pagingMap.put("pagingNumber", Integer.toString(pagingNumber));
		//pagingMap.put("keyword", keyword);
		//, @RequestParam(value="keyword") String keyword
		List<Space_InfoVO> inArr = spaceinfoservice.getSpaceInfoPageAll(pagingMap);
		for (int i = 0; i < inArr.size(); i++) {
			System.out.println(inArr.get(i));
		}
		log.info(inArr);
		model.addAttribute("spaceArr", inArr);
		model.addAttribute("currentPage", pagingNumber);

		return "Home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> spaceListPaging(@RequestParam(value = "currentPage") String currentPage,
			@RequestParam("pagingType") String pagingType, @RequestParam("keyword") String keyword) {
		Map<String, String> pagingMap = new HashMap<String, String>();
		log.info("param====>"+currentPage+"/"+pagingType+"/"+keyword);
		int pageSize = 8; //??????????????? ??????
		int pagingNumber = Integer.parseInt(currentPage); //???????????????
		String findkeyword = keyword;
		log.info("findkeyword===>"+findkeyword);
		int maxpage=spaceinfoservice.getCountAny(keyword);
		log.info("maxpage====>"+maxpage);
		log.info(pagingType);

		if (pagingType.equals("next") && pagingType != null) {// ????????????
			if(pagingNumber+pageSize<maxpage) {
				pagingNumber += pageSize;
			}
		} else if (pagingType.equals("prev") && pagingType != null) {// ????????????
			if (pagingNumber - pageSize < 0) {
				pagingNumber = 1;
			}else {
				pagingNumber -= pageSize;
			}
		}else { //?????? ???????????? ?????????????????? ???????????? ??????
			pagingNumber = 1;
		}
		log.info(pagingNumber);
		pagingMap.put("pagingSize", Integer.toString(pageSize));
		pagingMap.put("pagingNumber", Integer.toString(pagingNumber));
		pagingMap.put("findkeyword", findkeyword);
		
		List<Space_InfoVO> inArr = spaceinfoservice.getSpaceInfoPageAll(pagingMap);
		for (int i = 0; i < inArr.size(); i++) {
			System.out.println(inArr.get(i));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spaceArr", inArr);
		log.info(pagingMap.get("pagingNumber"));
		map.put("currentPage", pagingMap.get("pagingNumber"));

		return map;
	}
	@GetMapping(value="/search", produces = "application/json")
	@ResponseBody
	public List<Space_InfoVO> spaceSearch(@RequestParam(value="keyword") String keyword) {
		log.info("keyword===>"+keyword);
		List<Space_InfoVO> map = spaceinfoservice.selectByPname(keyword);
		log.info("result===>"+map);
		
		return map;
	}
	// MainHome
	@RequestMapping(value = "/MainHome2", method = RequestMethod.GET)
	public String MainHome(Model model) {
		logger.info("connected MainHome2.");
		return "MainHome2";
	}
	
	// Home
	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("connected Home.");
		return "ajax/Home";
	}
	

	//Reservation
//	@RequestMapping(value = "/Reservation", method = RequestMethod.GET)
//	public String services(Model model) {
//		logger.info("connected Reservation.");
//		return "ajax/Reservation";
//	}

	//Services
	@RequestMapping(value = "/Services", method = RequestMethod.GET)
	public String Services(Model model) {
		logger.info("connected Services.");
		return "ajax/Services";
	}

	
	@RequestMapping(value = "/user/MyPage", method = RequestMethod.GET)
	public String mypage(Model model) {
		logger.info("connected mypage.");
		return "ajax/Pages/MyPage";
	}


	@RequestMapping(value = "/naverMap")
	public String naverMap() {
		
		return "naverMap";
	}
  

  

	/*
	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String mylogin(Model model) {
		logger.info("connected Login.");
		return "ajax/Login";
	}
	*/
	@RequestMapping(value="/Join", method=RequestMethod.GET)
	public String Join(Model model) {
		logger.info("connected Join.");
		return "ajax/Join";
	}
	
	
	/*
	 * @RequestMapping(value="/UserList", method=RequestMethod.GET) public String
	 * UserList(Model model) { logger.info("connected UserList."); return
	 * "ajax/User/UserList"; }
	 */
	
	
	
	
	
}
