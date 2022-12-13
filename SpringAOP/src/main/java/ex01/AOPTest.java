package ex01;

import org.springframework.aop.framework.ProxyFactory;

public class AOPTest {
	public static void main(String[] args) {
		//1.�ٽ� ������ ���� ��ü => Target
		ServiceImpl target=new ServiceImpl();
		
		//2.���� ���ɻ��� => Advice
		AroundAdvice advice=new AroundAdvice();
		
		//3.ProxyFactory��ü �ΰ��� �������ִ� �븮��ü
		//    Proxy��ü ��� ==> Target�� ��ü������ ���ΰ� �ִ� ��ü�� Proxy��� �Ѵ�.
		//    Proxy�� ���������� Target�� ȣ��������, �߰��� �ʿ��� ���ɻ��׵��� ���ļ� Target�� ȣ���ϵ��� �ۼ��ȴ�.
		//    Proxy�� ������ aop�����ӟp���� �ڵ����� �����Ǵ� ���(auto-proxy)����� ����Ѵ�

		ProxyFactory pf=new ProxyFactory();
		pf.setTarget(target);
		//target�� serviceImple�� �����ϰ�
		pf.addAdvice(advice);
		//advice�� Ÿ���� ����Ǳ� �� ����aop�� �߰��Ѵ�.
		
		ServiceImpl proxy=(ServiceImpl)pf.getProxy(); //getProxy() ��ȯŸ��(object) serviceimpl�� ����ȯ
		proxy.sayHello("����","ö��","�ϴ�","����");
	}
}
