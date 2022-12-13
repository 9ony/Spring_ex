package ex02;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

//Aspect : adivce + pointcut
//�ٽɷ����� �ƴ����� �ڵ带 �����ϰ� ��������� ������� + ��������� �����Ұ�����
public class CommonAspect {
	//before
	public void trace1(JoinPoint jp) {
		Signature sign = jp.getSignature();
		System.out.println("before=========["+sign.toShortString()+"]");
	}
	//after
	public void trace2(JoinPoint jp) {
		Signature sign = jp.getSignature();
		System.out.println("after=========["+sign.toShortString()+"]");
	}
	//after-throwing
	public void trace3(JoinPoint jp, Exception e) {
		Signature sign = jp.getSignature();
		System.out.println("after-throwing(�����߻���)======["+sign.toShortString()+"]"+"  ��ȯ��=="+e);
	}
	//after-returning
	public void trace4(JoinPoint jp, Object result) {
		Signature sign = jp.getSignature();
		System.out.println("after-returning(��������)======["+sign.toShortString()+"]"+"  ��ȯ��=="+result.toString());
	}
	//around
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
