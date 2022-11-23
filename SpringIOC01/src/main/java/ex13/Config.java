package ex13;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
	@Bean(name="emp1")
	public Emp getBean() {
		return new Emp("최형태","노무부",4500);
	}
	
	@Bean(name="emp2")
	public Emp getBean2() {
		return new Emp("홍인숙","인사",5500);
	}
	
	@Bean
	public Member member() {
		return new Member();
	}
	
	@Bean
	public ServiceImpl service() {
		return new ServiceImpl();
	}
}
