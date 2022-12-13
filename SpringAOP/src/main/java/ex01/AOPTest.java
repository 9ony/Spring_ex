package ex01;

import org.springframework.aop.framework.ProxyFactory;

public class AOPTest {
	public static void main(String[] args) {
		//1.핵심 로직을 같는 객체 => Target
		ServiceImpl target=new ServiceImpl();
		
		//2.공통 관심사항 => Advice
		AroundAdvice advice=new AroundAdvice();
		
		//3.ProxyFactory객체 두개를 관리해주는 대리객체
		//    Proxy객체 얻기 ==> Target을 전체적으로 감싸고 있는 객체를 Proxy라고 한다.
		//    Proxy는 내부적으로 Target을 호출하지만, 중간에 필요한 관심사항들을 거쳐서 Target을 호출하도록 작성된다.
		//    Proxy는 스프링 aop프레임웤에서 자동으로 생성되는 방식(auto-proxy)방식을 사용한다

		ProxyFactory pf=new ProxyFactory();
		pf.setTarget(target);
		//target은 serviceImple로 설정하고
		pf.addAdvice(advice);
		//advice는 타겟이 실행되기 전 공통aop를 추가한다.
		
		ServiceImpl proxy=(ServiceImpl)pf.getProxy(); //getProxy() 반환타입(object) serviceimpl로 형변환
		proxy.sayHello("영의","철수","하니","제니");
	}
}
