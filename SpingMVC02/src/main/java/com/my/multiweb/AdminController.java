package com.my.multiweb;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.model.CategoryVO;
import com.shop.service.AdminService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/admin") //클래스앞에 붙이면 /admin은 달고들어온다
@Log4j
public class AdminController {
	
	@Inject
	private AdminService adminService;
	
	@GetMapping("/prodForm")
	public String productFrom(Model m) {
		List<CategoryVO> upCgList=adminService.getUpcategory();
		m.addAttribute("upCgList",upCgList);
		return "/admin/prodForm";
	}
	@PostMapping(value="/getDownCategory" , produces ="application/json")
	@ResponseBody
	public List<CategoryVO> DownCategory(@RequestParam("upCode") String upCode) {
		log.info("upcode --->"+upCode);
		List<CategoryVO> downCgList = adminService.getDowncategory(upCode);
		log.info("dwonCgList --->"+downCgList);
		return downCgList;
	}
}
