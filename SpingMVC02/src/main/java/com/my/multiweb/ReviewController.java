package com.my.multiweb;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.model.ReviewVO;
import com.shop.service.ReviewService;

import lombok.extern.log4j.Log4j;

/*Restfull방식
 * - URI + 메서드 방식에 따라 처리 로직을 달리한다.
 * /reviews 			GET : 상품 리뷰 목록을 조회
 * /reivews/{num}  		GET : 특정(num번) 리뷰글을 조회
 * /user/reviews    	POST : 리뷰 글쓰기 처리
 * /user/reviews/{num}  PUT: 특정 리뷰글을 수정
 * /user/reivews/{num} 	DELETE: 특정 리뷰글을 삭제
 * 
 * get/post 는 기본적으로 지원
 * 
 * put/delete 방식일경우 404에러 발생하는 경우가 많다.
 * 이런경우 web.xml에 가서 filter를 등록하자.
 * HiddenHttpMethidFilter, HttpPutFormContentFilter 등록하자
 * ========web.xml=============
 * ============================
 * 
 * <!-- Restful method======================================== -->
	<filter>
        <filter-name>hiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>hiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    
    <filter>
        <filter-name>HttpPutFormContentFilter</filter-name>
        <filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>HttpPutFormContentFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
 * ======================================================
 * */

@RestController
@Log4j
public class ReviewController {
	
	@Inject
	private ReviewService reviewService;
	
	@GetMapping(value="/reviews" , produces = "application/json")
	 public List<ReviewVO> reviewList(@RequestParam ("pnum") int pnum){
	 log.info("pnum====="+pnum); 
	 List<ReviewVO> arr = reviewService.listReview(pnum);
	  
	 return arr; 
	 }
	
	/* 강의코드
	 @GetMapping(value="/reviews", produces = "application/json")
	public List<ReviewVO> reviewList(HttpSession ses){
		Integer pnum=(Integer)ses.getAttribute("pnum");
		
		log.info("pnum==="+pnum);
		List<ReviewVO> arr=this.reviewService.listReview(pnum);
		return arr;
	}
	 */
	
	@PostMapping(value="/user/reviews" , produces = "application/xml")
	public ModelMap reviewInsert(@RequestParam("mfilename") MultipartFile mf,
			@ModelAttribute("rvo") ReviewVO rvo , HttpSession ses) {
		log.info("rvo post========>"+ rvo);
		ServletContext app = ses.getServletContext();
		//업로드 절대경로 얻어오기
		String upDir = app.getRealPath("/resources/review_images");
		log.info("upDir==="+upDir);
		File dir=new File(upDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		//업로드 처리
		
		try {
			mf.transferTo(new File(upDir, mf.getOriginalFilename()));
			rvo.setFilename(mf.getOriginalFilename());
		}catch (Exception e) {
			log.error(e);
		}
	
		int n = reviewService.addReview(rvo);
		
		ModelMap map=new ModelMap();
		map.addAttribute("result",n);
		
		return map;
	}
	
	@DeleteMapping(value="/user/reviews/{num}", produces = "application/json")
	public ModelMap reviewDelete(@PathVariable("num") int num) {
		log.info("DelMapping num === >"+num);
		int n = reviewService.deleteReview(num);
		ModelMap map = new ModelMap();
		map.addAttribute("result",n);
		return map;
	}
	@GetMapping(value="/reviews/{num}", produces = "application/json")
	public ReviewVO getReview(@PathVariable("num") int num) {
		
		ReviewVO rvo=reviewService.getReview(num);
		log.info("rvo===="+rvo);
		return rvo;
	}
	
}
