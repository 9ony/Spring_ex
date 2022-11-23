package ex13;
//의존 객체 자동 주입(Automatic Dependency Injection)
/* @Value :기본자료형, String 유형을 주입할 때 사용
 * @Autowired : byType으로 주입한다. 자료유형이 같은 객체가 있으면 주입한다. 찾는순서: 자료형> @Qualifier("빈이름")
 * @Resource  : byName으로 주입한다.  ==>pom.xml에 라이브러리를 등록해야 사용 가능하다. byType> @Qualifier
 * @Inject : ==>pom.xml에 라이브러리를 등록해야 사용 가능하다. byType> @Qualifier
 * 
 * */

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceImpl implements Service {
	
	//emp는 유니크한 빈이 아니므로 Qualifier("빈이름")을 넣어서 지정해준다
	/*
	 @Autowired
	 @Qualifier("emp1")
	 private Emp emp;
	 */
	
	//@Resource  : (name= 빈이름) 으로 주입해준다
	/*
	 * Resource를 사용하려면 javax.annotation.Resource를 import해야함
	 * 그럴러면 pom.xml에 dependency 라이브러리 추가
	 * <dependency>
	 *	    <groupId>javax.annotation</groupId>
	 *	    <artifactId>javax.annotation-api</artifactId>
	 *	    <version>1.3.2</version>
	 *	</dependency> 
	 */
	 @Resource(name="emp1") 
	 private Emp emp;
	 
	
	//메모리에 올라와있는 Member객체 자동으로 찾아준다
	@Autowired
	private Member user;
	
	
	@Override
	public void test1() {
		System.out.println("------emp정보--------");
		System.out.println(emp);
	}

	@Override
	public void test2() {
		System.out.println("----------member정보-----------");
		user.showInfo();
	}

}
