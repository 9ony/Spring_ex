package ex09;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAppTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ex09.Config.class);
		Emp emp=ctx.getBean("emp", Emp.class);
		System.out.println(emp);
		
		Service svc=ctx.getBean("service",Service.class);
		svc.test1();
	}
}
