package ex07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAppTest {

	public static void main(String[] args) {
		//FileSystemXmlApplicationContext ==> xml설정시
		//ClassPathXmlApplicationContext ==> xml설정시
		//AnnotationConfigApplicationContext==> 자바로 설정시

		// xml [x] 자바로 주입한것 룩업
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
		Emp e1 = ctx.getBean("e1",Emp.class); 
		System.out.println(e1);
		
		//bean name을 따로 설정하지않으면 메소드 이름으로
		System.out.println(ctx.getBean("empInfo2",Emp.class));
		
		/*결과 
			Emp [name=Miller, dept=Operation, salary=4000]
			Emp [name=Miller, dept=Operation, salary=4000]
			
			empInfo2가 e1의 객체를 받아와서 setter로 값을 설정해주면서
			e1도 setter로 설정한 empInfo2의 값과 똑같이나온다
			그 이유는 기본적으로 bean은 싱글톤으로 생성되어서 객체가 하나만 생성이 되기 때문 
			
			그걸 방지하기위해서 어노테이션 @Scope(value="prototype") 을 붙여주면 객체를 매번 생성한다.
			Emp [name=King, dept=Research, salary=5000]
			Emp [name=Miller, dept=Operation, salary=4000]
		*/
		
		Emp e3 = ctx.getBean("empInfo3",Emp.class);
		System.out.println(e3);
		
		
		Service s1 = ctx.getBean("service",Service.class);
		s1.test1();
	}

}
