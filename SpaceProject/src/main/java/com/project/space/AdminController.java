package com.project.space;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.space.domain.Mem_InfoVO;
import com.project.space.user.service.Mem_InfoService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class AdminController {
	@Inject
	Mem_InfoService memberservice;
	
	@RequestMapping(value="/admin/AdminHome", method=RequestMethod.GET)
	public String AdminPage(Model model) {
		log.info("connected AdminPage.");
		List<Mem_InfoVO> memArr = memberservice.listUser(null);
		log.info(memArr);
		model.addAttribute("memArr", memArr);
		return "ajax/AdminPage/AdminHomePage";
	}
	@GetMapping("admin/adminpage")
	public String adminPage() {
	      
		return "ajax/AdminPage/AdminNav";
	}
	@GetMapping("admin/userlistform")
	public String userList() {
	      
		return "ajax/AdminPage/UserList";
	}
	@GetMapping("admin/userbookingform")
	public String userBooking() {
	      
		return "ajax/AdminPage/UserBooking";
	}
	@GetMapping("admin/hostlistform")
	public String hostList() {
	
		return "ajax/AdminPage/HostList";
	}
	@GetMapping("admin/hostuploadform")
	public String hostUpload() {
		
		return "ajax/AdminPage/HostUpload";
	}
	//===================뷰========================
	@GetMapping("admin/userlist")
	@ResponseBody
	public List<Mem_InfoVO> getUserList(Model m){
		List<Mem_InfoVO> memArr = memberservice.listUser(null);
		log.info("getUserList userArr====>"+memArr);
		return memArr;
	}
}
