package ex02;

public class HelloApp {

	public static void main(String[] args) {
		MessageBean mb = new MessageBeanKo();
		mb.sayHello("Scott");
		//객체타입은 인터페이스로하고 객체를 한국어로 설정한 객체로 생성한다
		//인터페이스로 객체교체를 완화시켜서 결합도가 느슨해짐
	}

}
