package com.project.space;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	// HOME
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("connected index.");
		return "MainHome";
	}

	//Reservation
	@RequestMapping(value = "/Reservation", method = RequestMethod.GET)
	public String services(Model model) {
		logger.info("connected Reservation.");
		model.addAttribute("selmenu", "Reservation");
		return "ajax/Reservation";
	}

	//Services
	@RequestMapping(value = "/Services", method = RequestMethod.GET)
	public String Services(Model model) {
		logger.info("connected Services.");
		model.addAttribute("selmenu", "Services");
		model.addAttribute("test", "테스트입니다");
		return "ajax/Services";
	}
	//Services
	@GetMapping(value="/Services/test",produces = "application/json")
	@ResponseBody
	public ModelMap Servicesbtn2(@RequestParam("q") String query) {
		logger.info("connected Services test.");
		logger.info("query= "+query);
		ModelMap map = new ModelMap();
		map.put("test", query);
		return map;
	}

	//Contact
	@RequestMapping(value = "/Contact", method = RequestMethod.GET)
	public String contact(Model model) {
		logger.info("connected contact.");
		return "ajax/Contact";
	}

//	//Mypage
//	@RequestMapping(value = "/MyPage", method = RequestMethod.GET)
//	public String mypage(Model model) {
//		logger.info("connected mypage.");
//		return "ajax/MyPage";
//	}
//
//	//MyReservation
//	@RequestMapping(value = "/MyReservation", method = RequestMethod.GET)
//	public String myreservation(Model model) {
//		logger.info("connected myreservation.");
//		return "ajax/MyReservation";
//	}

	// for test mapping
	@RequestMapping(value = "/index12", method = RequestMethod.GET)
	public String index12(Model model) {
		logger.info("connected index12.");
		return "ajax/index12";
	}

	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String HomeAjax(Model model) {
		logger.info("connected HomeAjax.");
		return "ajax/Home";
	}
	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String myLogin(Model model) {
		logger.info("connected LoginAjax.");
		return "ajax/Login";
	}

}
