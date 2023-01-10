package com.project.space;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.space.domain.Mem_InfoVO;
import com.project.space.user.service.Mem_InfoService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin")
public class AdminController {
	@Inject
	Mem_InfoService memberservice;
	
	@RequestMapping(value="/AdminHome", method=RequestMethod.GET)
	public String AdminPage(Model model) {
		
		return "ajax/AdminPage/AdminHomePage";
	}
	@GetMapping("/adminNav")
	public String adminPage() {
	      
		return "ajax/AdminPage/AdminNav";
	}
	
	@GetMapping("/userbookingform")
	public String userBooking() {
	      
		return "ajax/AdminPage/UserBooking";
	}
	@GetMapping("/hostlistform")
	public String hostList() {
	
		return "ajax/AdminPage/HostList";
	}
	
	//===================유저 조회========================
	@GetMapping("/userlistform")
	public String userList() {
	      
		return "ajax/AdminPage/UserList";
	}
	
	@PostMapping(value="/userlist" , produces = "application/json")
	@ResponseBody
	public List<Mem_InfoVO> getUserList(Model m){
		List<Mem_InfoVO> memArr = memberservice.listUser(null);
		log.info("getUserList userArr====>"+memArr);
		return memArr;
	}
	@PostMapping(value="/search" , produces = "application/json")
	@ResponseBody
	public List<Mem_InfoVO> getSearchUser(@RequestBody Map<String,String> filter , HttpServletRequest req){
		log.info("test");
		log.info(filter.get("keyword"));
		log.info(filter.get("maxFmage"));
		log.info(filter);
		List<Mem_InfoVO> memArr = memberservice.searchUserByFilter(filter);
		log.info(memArr);
		log.info(memArr.get(0).getMdate());
		String test = (String)memArr.get(0).getMdate().toString();
		log.info(test);
		return memArr;
	}
	//===============공간등록내역조회============================
	@GetMapping("/spacelistform")
	public String hostUpload() {
		
		return "ajax/AdminPage/SpaceList";
	}
}
