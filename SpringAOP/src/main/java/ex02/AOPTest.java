package ex02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ex02/aop.xml");
		boardService bs = ctx.getBean("boardSvc", boardService.class);
		BoardVO vo = new BoardVO(1,"첫번째 게시글 등록합니다","홍길동");
		BoardVO err_vo = null;
		bs.insertBoard(err_vo);
		
	}

}
