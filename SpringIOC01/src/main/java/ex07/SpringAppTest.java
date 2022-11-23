package ex07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAppTest {

	public static void main(String[] args) {
		//FileSystemXmlApplicationContext ==> xml������
		//ClassPathXmlApplicationContext ==> xml������
		//AnnotationConfigApplicationContext==> �ڹٷ� ������

		// xml [x] �ڹٷ� �����Ѱ� ���
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
		Emp e1 = ctx.getBean("e1",Emp.class); 
		System.out.println(e1);
		
		//bean name�� ���� �������������� �޼ҵ� �̸�����
		System.out.println(ctx.getBean("empInfo2",Emp.class));
		
		/*��� 
			Emp [name=Miller, dept=Operation, salary=4000]
			Emp [name=Miller, dept=Operation, salary=4000]
			
			empInfo2�� e1�� ��ü�� �޾ƿͼ� setter�� ���� �������ָ鼭
			e1�� setter�� ������ empInfo2�� ���� �Ȱ��̳��´�
			�� ������ �⺻������ bean�� �̱������� �����Ǿ ��ü�� �ϳ��� ������ �Ǳ� ���� 
			
			�װ� �����ϱ����ؼ� ������̼� @Scope(value="prototype") �� �ٿ��ָ� ��ü�� �Ź� �����Ѵ�.
			Emp [name=King, dept=Research, salary=5000]
			Emp [name=Miller, dept=Operation, salary=4000]
		*/
		
		Emp e3 = ctx.getBean("empInfo3",Emp.class);
		System.out.println(e3);
		
		
		Service s1 = ctx.getBean("service",Service.class);
		s1.test1();
	}

}
