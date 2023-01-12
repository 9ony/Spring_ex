package com.project.space.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.space.admin.mapper.AdminMapper;
import com.project.space.admin.service.AdminMemberInquiryVO;
import com.project.space.domain.HashtagVO;
import com.project.space.domain.PagingVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminServiceImp implements AdminService{
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public List<AdminMemberInquiryVO> searchUserByFilter(Map<String, String> filter) {
		log.info("filter==>"+filter);
		return adminMapper.searchUserByFilter(filter);
	}
	
	@Override
	public List<HashtagVO> getHashTagAll(){
		return adminMapper.getHashTagAll();
	}

	@Override
	public List<AdminMemberInquiryVO> listUser(PagingVO pvo) {
		return adminMapper.listUser(pvo);
	}

	@Override
	public List<AdminSpaceInquiryVO> listSpace(PagingVO pvo) {
		// TODO Auto-generated method stub
		return adminMapper.listSpace(pvo);
	}
}
