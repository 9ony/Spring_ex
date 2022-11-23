package ex08;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//ConfigŬ������ ȯ�漳������ ����Ѵٴ� �ǹ��� �̳����̼� [�ڹٷ� ������]
@Configuration
public class Config {
	//�������� �⺻������ ���� �̱��� ��ü(���ϰ�ü)�� �����ȴ�.
	
	//<bean id="e1" class="ex08.Emp"/>
	@Bean(name="e1") //DL ���
	@Scope(value = "prototype")// �Ź� �ٸ���ü�� �����Ѵ�.
	//@Scope(value = "singleton")// default ��
	public Emp empInfo() { //Emp ��ü�� �������ִ� ������
		return new Emp("King","Research",5000); 
	}
	
	
	//bean�� name�Ӽ��� ���� ������ ���� �̸��� �޼��� �̸��� ��name�� �ȴ�. ��� name=empInfo2
	@Bean
	public Emp empInfo2() {
		Emp e = this.empInfo(); //empInfo�� �޾ƿ�
		e.setName("Miller");
		e.setDept("Operation");
		e.setSalary(4000);
		return e;
	}
	
	//Emp���� ��ȯ�ϴ� �޼��带 �����ϼ���
	@Bean
	public Emp empInfo3() {
		return new Emp("Scott","Analyst",3000);
	}
	
	//ServiceImpl ���� ��ȯ�ϴ� �޼��带 �����ϼ���
	@Bean
	public ServiceImpl service() {
		ServiceImpl s =new ServiceImpl();
		s.setEmp(this.empInfo3());
		return s;
	}
}
