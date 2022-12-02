package com.shop.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shop.mapper.ReviewMapper;
import com.shop.model.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Inject
	ReviewMapper reviewMapper;
	
	@Override
	public int addReview(ReviewVO rvo) {
		// TODO Auto-generated method stub
		return reviewMapper.addReview(rvo);
	}

	@Override
	public List<ReviewVO> listReview(int pnum_fk) {
		// TODO Auto-generated method stub
		return reviewMapper.listReview(pnum_fk);
	}

	@Override
	public int getReviewCount(int pnum_fk) {
		// TODO Auto-generated method stub
		return reviewMapper.getReviewCount(pnum_fk);
	}

	@Override
	public ReviewVO getReview(int num) {
		// TODO Auto-generated method stub
		return reviewMapper.getReview(num);
	}

	@Override
	public int updateReview(ReviewVO rvo) {
		// TODO Auto-generated method stub
		return reviewMapper.updateReview(rvo);
	}

	@Override
	public int deleteReview(int num) {
		// TODO Auto-generated method stub
		return reviewMapper.deleteReview(num);
	}

}
