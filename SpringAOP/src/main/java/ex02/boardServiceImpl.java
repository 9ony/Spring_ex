package ex02;

import java.util.ArrayList;
import java.util.List;
//Target : �ٽɷ����� ���� Ŭ����
public class boardServiceImpl implements boardService {
	List<BoardVO> boardArr=new ArrayList<>();
	
	@Override
	public int insertBoard(BoardVO vo) {
		System.out.println("�ٽɷ��� insertBoard �޼���");
		System.out.println(vo.getTitle()+"���� ����մϴ�");
		boardArr.add(vo);
		
		return boardArr.size();
	}

}
