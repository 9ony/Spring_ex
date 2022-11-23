package ex08;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringAppTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ex08/applicationContext.xml");
		Emp emp=ctx.getBean("emp",Emp.class);
		System.out.println(emp);
		Service svc = ctx.getBean("svc",Service.class);
		svc.test1();
	}
}
