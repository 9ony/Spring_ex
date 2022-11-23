package ex01;

public class HelloApp {

	public static void main(String[] args) {
		//System.out.println("hello app!!");
		//MessageBeanKo mb=new MessageBeanKo();
		//mb.sayHello("홍길동");
		//2) 한국 서비스 -> 영어권 서비스 변경예
		//==>교체하기 위해선 객체를 다시 수정하고 호출해야된다.
		MessageBeanEn mb=new MessageBeanEn();
		mb.sayHello("홍길동");
		/*1)
		 * HelloApp이 MessageBeanKo객체를 사용한다
		 * => HelloApp이 MessageBeanKo에 의존한다고 표현
		 * : 이 때 의존성 있는 객체간의 결합도가 중요하다!
		 * 결합도가 강하면 향후 객체들을 수정or교체 하고자 할때 문제가 될 수 있다.
		 * 
		 */
	}

}
