package ex13;
//���� ��ü �ڵ� ����(Automatic Dependency Injection)
/* @Value :�⺻�ڷ���, String ������ ������ �� ���
 * @Autowired : byType���� �����Ѵ�. �ڷ������� ���� ��ü�� ������ �����Ѵ�. ã�¼���: �ڷ���> @Qualifier("���̸�")
 * @Resource  : byName���� �����Ѵ�.  ==>pom.xml�� ���̺귯���� ����ؾ� ��� �����ϴ�. byType> @Qualifier
 * @Inject : ==>pom.xml�� ���̺귯���� ����ؾ� ��� �����ϴ�. byType> @Qualifier
 * 
 * */

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceImpl implements Service {
	
	//emp�� ����ũ�� ���� �ƴϹǷ� Qualifier("���̸�")�� �־ �������ش�
	/*
	 @Autowired
	 @Qualifier("emp1")
	 private Emp emp;
	 */
	
	//@Resource  : (name= ���̸�) ���� �������ش�
	/*
	 * Resource�� ����Ϸ��� javax.annotation.Resource�� import�ؾ���
	 * �׷����� pom.xml�� dependency ���̺귯�� �߰�
	 * <dependency>
	 *	    <groupId>javax.annotation</groupId>
	 *	    <artifactId>javax.annotation-api</artifactId>
	 *	    <version>1.3.2</version>
	 *	</dependency> 
	 */
	 @Resource(name="emp1") 
	 private Emp emp;
	 
	
	//�޸𸮿� �ö���ִ� Member��ü �ڵ����� ã���ش�
	@Autowired
	private Member user;
	
	
	@Override
	public void test1() {
		System.out.println("------emp����--------");
		System.out.println(emp);
	}

	@Override
	public void test2() {
		System.out.println("----------member����-----------");
		user.showInfo();
	}

}
