package ex02;

import java.util.ArrayList;
import java.util.List;
//Target : 핵심로직을 갖는 클래스
public class boardServiceImpl implements boardService {
	List<BoardVO> boardArr=new ArrayList<>();
	
	@Override
	public int insertBoard(BoardVO vo) {
		System.out.println("핵심로직 insertBoard 메서드");
		System.out.println(vo.getTitle()+"글을 등록합니다");
		boardArr.add(vo);
		
		return boardArr.size();
	}

}
