package ex07;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//Config클래스를 환경설정으로 사용한다는 의미의 이노테이션 [자바로 인젝션]
@Configuration
public class Config {
	//스프링은 기본적으로 빈을 싱글톤 객체(단일객체)로 생성된다.
	
	//<bean id="e1" class="ex07.Emp"/>
	@Bean(name="e1") //빈 name 설정
	@Scope(value = "prototype")// 매번 다른객체로 생성한다.
	//@Scope(value = "singleton")// default 값
	public Emp empInfo() { 
		return new Emp("King","Research",5000); 
	}
	
	
	//bean에 name속성을 주지 않으면 빈의 이름은 메서드 이름이 빈name이 된다. 고로 name=empInfo2
	@Bean
	public Emp empInfo2() {
		Emp e = this.empInfo(); //empInfo를 받아옴
		e.setName("Miller");
		e.setDept("Operation");
		e.setSalary(4000);
		return e;
	}
	
	//Emp빈을 반환하는 메서드를 구성하세요
	@Bean
	public Emp empInfo3() {
		return new Emp("Scott","Analyst",3000);
	}
	
	//ServiceImpl 빈을 반환하는 메서드를 구성하세요
	@Bean
	public ServiceImpl service() {
		ServiceImpl s =new ServiceImpl();
		s.setEmp(this.empInfo3());
		return s;
	}
}
