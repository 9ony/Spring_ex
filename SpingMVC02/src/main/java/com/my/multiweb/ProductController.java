package com.my.multiweb;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.model.ProductVO;
import com.shop.service.ShopService;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class ProductController {
	
	@Inject
	private ShopService shopService;
	
	//HIR,NEW,BEST 별로 상품목록 가져오기
	@GetMapping("/prodPspec")
	public String productByPsepc(Model m, @RequestParam(name="pspec", defaultValue="HIT") String pspec) {
		//log.info("pspec====="+pspec);
		List<ProductVO> pList = shopService.selectByPspec(pspec);
		
		m.addAttribute("pList",pList);
		
		return "shop/mallHit";
	}
	@GetMapping(value="/prodDetail")
	public String prodDetail(Model m ,  @RequestParam(defaultValue="0") int pnum) {
		if(pnum==0) {
			return "redirect:index";
		}
		//log.info("prodDetail pnum==>"+pnum);
		ProductVO prod = shopService.selectByPnum(pnum);
		log.info("prodDetail ===> "+prod);
		m.addAttribute("prod",prod);
		
		return "shop/prodDetail";
	}

	/*
	@GetMapping("/prodDetail")
	public String productDetail(Model m, @RequestParam(defaultValue="0") int pnum, HttpSession ses) {
		if(pnum==0) {
			return "redirect:index";
		}
		ses.setAttribute("pnum", pnum);
		ProductVO vo=this.shopService.selectByPnum(pnum);
		m.addAttribute("prod",vo);
		return "shop/prodDetail";
	}//-------------------------------------
	 */
	@GetMapping("/openPop")
	public String openPop() {
		return "shop/openPop";
	}
}
