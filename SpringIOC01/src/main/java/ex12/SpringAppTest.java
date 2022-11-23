package ex12;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringAppTest {

	public static void main(String[] args) {
		//admin1 룩업해서 info1() 호출하기
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		//admin2 룩업해서 info1() 호출하기
		ctx.getBean("adminInfo",AdminVO.class).info();
	}

}
