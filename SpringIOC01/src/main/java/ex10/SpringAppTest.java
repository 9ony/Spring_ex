package ex10;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringAppTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:ex10/application.xml");
		MessageBean mb=ctx.getBean("mb",MessageBean.class);
		mb.sayhello();
		mb.sayHi("¿µÈñ","¼øÈñ","Ã¶ÀÌ");
		
		
		
	}

}
