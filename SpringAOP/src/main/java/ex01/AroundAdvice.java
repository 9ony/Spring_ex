package ex01;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

//공통 aop
//advice는 조인된 메서드 호출 전후에 실행된다.
public class AroundAdvice implements MethodInterceptor{
	@Override
	public Object invoke(MethodInvocation invo) throws Throwable{
		Object obj=null;
		System.out.println("I am AroundAdvice1: "+invo.getMethod()+"호출전 =============");
		obj=invo.proceed(); //메서드가 수행
		System.out.println("I am AroundAdvice2: "+invo.getMethod()+"호출후 =============");
		return obj;
	}
}
