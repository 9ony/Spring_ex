package ex03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ex03/aop.xml");
		boardService bs = ctx.getBean("boardSvc", boardService.class);
		BoardVO vo = new BoardVO(2,"�ι�° �Խñ� ����մϴ�","ȫ�浿");
		BoardVO err_vo = null;
		bs.insertBoard(vo);
		
	}

}
