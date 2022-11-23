package ex13;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAppTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ex13.Config.class);
		Service s = ctx.getBean("service",Service.class);
		
		s.test2();
		
		s.test1();
		//emp는 nonunieuq한 빈이아니고 2개가 발견되서 에러가나옴
		//그러므로 autowired하면 qualifier로 "빈이름"을 지정해줘야된다
	}
}
