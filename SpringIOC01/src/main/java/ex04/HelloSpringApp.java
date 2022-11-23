package ex04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/* ������ �����̳� : ���� �����Ѵ�.
 * 			     ������ �����̳ʴ� �� ��ü�� �����ϰ� ������, �� ��ü���� ���� ���踦 �������ش�.
               BeanFactory�� ApplicationContext�� �����̳� ������ �����ϴ� �������̽�
 * 1 BeanFactory (�������̽�)
 * 2 ApplicationContext (BeanFactory �� ��ӹ��� ���� �������̽�.)
 * 3 WebApplicationContext (�������̽�. �� ���ø����̼��� ���� ApplicationContext��.
         �ϳ��� ������(��, �ϳ���ServletContext) ���� �� �� �̻��� WebApplicationContext�� ���� �� �ִ�).
 * 
 * DI(dependency Injection) : ������ ����
 * 1 ��ü ���� ���踦 �����ϰ� �����ϵ��� ���ִ� ��� ���� �ϳ�.
 * 2 �����Ǵ� ��ü�� ���������� �����ϴ� ��ü���� �������� �ʰ� 
 * �����̳ʿ��� ������ �ؼ� ����ϴ� ����̴�. 
 * 
 * �����̳� ����
 * - FileSystemXmlApplicationContext => xml�� ����
 * - ClassPathXmlApplicationContext => xml�� ����
 * - AnnotationConfigApplicationContext => �ڹٷ� ����
 */

import ex03.MessageBean;

public class HelloSpringApp {

	public static void main(String[] args) {
		//������ �����̳� �����ؼ� mb1��� �ѵ� sayhello()ȣ���ϱ�
		String config = "src/main/java/ex04/appContext.xml";
		ApplicationContext ctx = new FileSystemXmlApplicationContext(config);
		
		//DL : (Dendency Lookup) �޸𸮿� �ö� �ִ� ��ü�� �̸����� Ž��
		//Message mb = (Message)ctx.getBean("mb1");
		Message mb = ctx.getBean("mb1",Message.class);
		Message mb2 = ctx.getBean("mb2",Message.class);
		// getBean(property id, class); �̷��� ����ȯ�� ����
		mb.sayhello();
		mb.sayHi("����","�ٶ�","����");
		System.out.println("======================");
		mb2.sayhello();
		mb2.sayHi("����","����","ö��");

	}

}
