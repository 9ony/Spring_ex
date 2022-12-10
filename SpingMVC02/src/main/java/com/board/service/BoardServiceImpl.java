package com.board.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.mapper.BoardMapper;
import com.board.model.BoardVO;
import com.board.model.PagingVO;


@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardMapper boardMapper;
	
	@Override
	public int insertBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return boardMapper.insertBoard(board);
	}

	@Override
	public List<BoardVO> selectBoardAll(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardAll(map);
	}

	@Override
	public List<BoardVO> selectBoardAllPaging(PagingVO paging) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardAllPaging(paging);
	}

	@Override
	public List<BoardVO> findBoard(PagingVO paging) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCount(PagingVO paging) {
		// TODO Auto-generated method stub
		return boardMapper.getTotalCount(paging);
	}

	@Override
	public BoardVO selectBoardByIdx(Integer idx) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardByIdx(idx);
	}

	@Override
	public int updateReadnum(Integer idx) {
		// TODO Auto-generated method stub
		return boardMapper.updateReadnum(idx);
	}

	@Override
	public String selectPwd(Integer idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteBoard(Integer idx) {
		// TODO Auto-generated method stub
		return boardMapper.deleteBoard(idx);
	}

	@Override
	public int updateBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return boardMapper.updateBoard(board);
	}

	@Override
	public int rewriteBoard(BoardVO board) {
		//[1] 부모글(원글)의 글번호(num)로 부모글의 refer(글그룹번호), lev(답변레벨), sunbun(순번) 가져오기
		//==> select문
		BoardVO parent=this.selectRefLevSunbun(board.getNum());
		//[2] 기존에 달린 답변글 들이 있다면 내 답변글을 insert하기 전에 기존의 답변글들의 sunbun을 하나씩 증가시키자.
		//==> update문
		int up=updateSunbun(parent);

		//[3] 내가 쓴 답변 글을 insert 한다===> insert문
		//내가 쓴 답변글==>board
		//부모글 ==>parent (부모글의 refer,lev,sunbun)
		board.setRefer(parent.getRefer());
		board.setLev(parent.getLev()+1);
		board.setSunbun(parent.getSunbun()+1);
		
		return boardMapper.rewriteBoard(board);
	}

	@Override
	public BoardVO selectRefLevSunbun(int idx) {
		// TODO Auto-generated method stub
		return this.boardMapper.selectRefLevSunbun(idx);
	}

	@Override
	public int updateSunbun(BoardVO parent) {
		// TODO Auto-generated method stub
		return boardMapper.updateSunbun(parent);
	}

}
