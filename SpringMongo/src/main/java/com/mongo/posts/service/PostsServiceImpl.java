package com.mongo.posts.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mongo.posts.mapper.PostsMapper;
import com.mongo.posts.domain.PostVO;

@Service(value="postsServiceImpl")
public class PostsServiceImpl implements PostsService {
	
	@Resource(name="postsMapperImpl")
	private PostsMapper pMapper;

	@Override
	public int insertPosts(PostVO vo) {
		// TODO Auto-generated method stub
		return pMapper.insertPosts(vo);
	}

	@Override
	public List<PostVO> listPosts() {
		// TODO Auto-generated method stub
		return pMapper.listPosts();
	}

	@Override
	public int deletePosts(String id) {
		// TODO Auto-generated method stub
		return pMapper.deletePosts(id);
	}
	@Override
	public PostVO selectPosts(String id) {
		return pMapper.selectPosts(id);
	}
	@Override
	public int updatePosts(PostVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
