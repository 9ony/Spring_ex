package com.my.multiweb;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.service.ReviewService;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class ReviewController {

	//private ReviewService reviewService;
	
//	@GetMapping("/review")
//	public String reviewForm() {
//		return "review/reviewForm";
//	}
}
