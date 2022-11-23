package ex06;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceAppTest {
	public static void main(String[] args) {
		//s1을 룩업한 뒤 test1()호출하기
		ApplicationContext ctx=new ClassPathXmlApplicationContext("ex06/collection.xml");
		Service s1=ctx.getBean("s1",Service.class);
		s1.test1();
		ctx.getBean("s2",Service.class).test2();
		ctx.getBean("s3",Service.class).test3();
		ctx.getBean("s4",Service.class).test4();
		
	}
}
