package ex01;

public class HelloApp {

	public static void main(String[] args) {
		//System.out.println("hello app!!");
		//MessageBeanKo mb=new MessageBeanKo();
		//mb.sayHello("ȫ�浿");
		//2) �ѱ� ���� -> ����� ���� ���濹
		//==>��ü�ϱ� ���ؼ� ��ü�� �ٽ� �����ϰ� ȣ���ؾߵȴ�.
		MessageBeanEn mb=new MessageBeanEn();
		mb.sayHello("ȫ�浿");
		/*1)
		 * HelloApp�� MessageBeanKo��ü�� ����Ѵ�
		 * => HelloApp�� MessageBeanKo�� �����Ѵٰ� ǥ��
		 * : �� �� ������ �ִ� ��ü���� ���յ��� �߿��ϴ�!
		 * ���յ��� ���ϸ� ���� ��ü���� ����or��ü �ϰ��� �Ҷ� ������ �� �� �ִ�.
		 * 
		 */
	}

}
