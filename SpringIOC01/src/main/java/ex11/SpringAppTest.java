package ex11;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringAppTest {

	public static void main(String[] args) {
		//admin1 룩업해서 info1() 호출하기
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/java/ex11/appContext.xml");
		//admin2 룩업해서 info1() 호출하기
		ctx.getBean("admin1",AdminVO.class).info();
		ctx.getBean("admin2",AdminVO.class).info();
	}

}
