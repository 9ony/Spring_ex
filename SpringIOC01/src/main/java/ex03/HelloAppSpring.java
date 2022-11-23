package ex03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
//IOC ������� Spring FrameWork�� ���´�
public class HelloAppSpring {
	
	public static void main(String[] args) {
		String config = "src/main/java/ex03/applicationContext.xml";
		//������ �����̳�
		ApplicationContext ctx = new FileSystemXmlApplicationContext(config);
		
		//DL : (Dendency Lookup) �޸𸮿� �ö� �ִ� ��ü�� �̸����� Ž��
		MessageBean mb = (MessageBean)ctx.getBean("mbko");
		mb.sayHello("Spring");
	}
}
