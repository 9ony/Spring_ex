package ex03;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class CommonAspect {
	
	//before
	@Before("execution(* ex03.*ServiceImpl.insert*(..))")
	public void trace1(JoinPoint jp) {
		Signature sign = jp.getSignature();
		System.out.println("before=========["+sign.toShortString()+"]");
	}
	//after
	@After("execution(* ex03.*ServiceImpl.insert*(..))")
	public void trace2(JoinPoint jp) {
		Signature sign = jp.getSignature();
		System.out.println("after=========["+sign.toShortString()+"]");
	}
	//after-throwing
	@AfterThrowing(value="execution(* ex03.*ServiceImpl.insert*(..))", throwing = "e")
	public void trace3(JoinPoint jp, Exception e) {
		Signature sign = jp.getSignature();
		System.out.println("after-throwing(에러발생시)======["+sign.toShortString()+"]"+"  반환값=="+e);
	}
	//after-returning
	@AfterReturning(value="execution(* ex03.*ServiceImpl.insert*(..))", returning = "result")
	public void trace4(JoinPoint jp, Object result) {
		Signature sign = jp.getSignature();
		System.out.println("after-returning(정상실행시)======["+sign.toShortString()+"]"+"  반환값=="+result.toString());
	}
	//around
	@Around("execution(* ex03.*ServiceImpl.insert*(..))")
	public Integer trace5(ProceedingJoinPoint jp) {
		Signature sign = jp.getSignature();
		System.out.println("around(before)=========["+sign.toShortString()+"] around start===");
		try {
			Integer n=(Integer)jp.proceed();
			return n;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}finally {
			System.out.println("around(after)=========["+sign.toShortString()+"] around end===");
		}
	}
}
