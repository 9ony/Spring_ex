package com.project.space.user.service;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.space.domain.Mem_InfoVO;
import com.project.space.domain.NotUserException;
import com.project.space.domain.PagingVO;
import com.project.space.user.mapper.Mem_InfoMapper;

import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class Mem_InfoServiceImpl implements Mem_InfoService {
	
	@Autowired
	private PasswordEncoder pwencoder; //�븫�샇�솕 媛앹껜
	
	@Inject
	private Mem_InfoMapper memberMapper;
	
	@Override
	public int createUser(Mem_InfoVO memvo) {
		String beforeEncoding = memvo.getMpwd();
		log.info("�븫�샇�솕�븯湲� �쟾 鍮꾨�踰덊샇 �솗�씤==>"+memvo.getMpwd());
		String afterEncoding = pwencoder.encode(beforeEncoding);
		memvo.setMpwd(afterEncoding);
		log.info("�븫�샇�솕�맂吏� 鍮꾨�踰덊샇 �솗�씤==>"+memvo.getMpwd());
		return memberMapper.createUser(memvo);
	}

	@Override
	public int getUserCount(PagingVO pvo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Mem_InfoVO> listUser(PagingVO pvo) {
		return memberMapper.listUser(pvo);
	}

	@Override
	public int idCheck(String userid) {
		// TODO Auto-generated method stub
		return memberMapper.idCheck(userid);
	}

	@Override
	public int deleteUser(Mem_InfoVO vo) {
		log.info(vo.getUserid());
		return memberMapper.deleteUser(vo);
	}

	@Override
	public int updateUser(Mem_InfoVO user) {
		String afterEncoding = pwencoder.encode(user.getMpwd());
		user.setMpwd(afterEncoding);
		return memberMapper.updateUser(user);
	}

	@Override
	public Mem_InfoVO getUser(String userid) {
		// TODO Auto-generated method stub
		return memberMapper.getUser(userid);
	}

	@Override
	public Mem_InfoVO findUser(Mem_InfoVO findUser) throws NotUserException {
		Mem_InfoVO user=memberMapper.findUser(findUser);
		if(user==null) {
			throw new NotUserException("議댁옱�븯吏� �븡�뒗 �븘�씠�뵒�엯�땲�떎");
		}
		return user;
	}

	@Override
	public Mem_InfoVO loginCheck(String userid, String mpwd) throws NotUserException {
		Mem_InfoVO user=this.getUser(userid);
		log.info("mem_infoservice logincheck user==>"+user);
		if(user==null) {
			throw new NotUserException("議댁옱�븯吏� �븡�뒗 �븘�씠�뵒�엯�땲�떎");
		}
		if(!pwencoder.matches(mpwd,user.getMpwd() )) {
		//if(!user.getMpwd().equals(mpwd)) { 
			throw new NotUserException("鍮꾨�踰덊샇媛� �씪移섑븯吏� �븡�뒿�땲�떎");
		}
		
		return user;
	}
	
	@Override
	public List<Mem_InfoVO> searchUserByFilter(Map<String, String> filter) {
		// TODO Auto-generated method stub
		return memberMapper.searchUserByFilter(filter);
	}
	
	@Override
	public int getStatusByUserid(String userid) {
		return memberMapper.getStatusByUserid(userid);
	}
	
	@Override
	public Mem_InfoVO pwCheck(String userid, String mpwd) throws NotUserException {
		Mem_InfoVO user=memberMapper.getUser(userid);
		if(user==null) {
			throw new NotUserException("회원이 아닙니다");
		}
		
		if(!pwencoder.matches(mpwd, user.getMpwd())) {
		
			throw new NotUserException("비밀번호가 일치하지 않습니다");
		}
		
		return user;
	}

	@Override
	public List<Mem_InfoVO> listBankcode() {
		return memberMapper.listBankcode();
	}
	
	@Override
	   public int updateUserPoint(String userid, int parseInt) {
	      Map<String, Object> map = new java.util.HashMap<String, Object>();
	      map.put("userid", userid);
	      map.put("point", parseInt);
	      return memberMapper.updateUserPoint(map);
	   }

}
