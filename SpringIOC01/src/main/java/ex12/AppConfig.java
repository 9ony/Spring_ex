package ex12;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfig {
	//admin.properties파일에 기술된 adminId와 adminPwd값을 주입
	@Value("${adminId}") //admin.propertires에 있는 키의값을 넣는다는 의미에 어노테이션(@Value)
	private String id;
	@Value("${adminPwd}")
	private String pwd;
	
	@Bean
	public AdminVO adminInfo() {
		AdminVO vo = new AdminVO();
		vo.setAdminId(id);
		vo.setAdminPwd(pwd);
		return vo;
	}
	
	//외부에있는 properties 데이터를 얻어오는 메서드
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer conf
		=new PropertySourcesPlaceholderConfigurer();
		
		Resource[] resources=new Resource[1];
		resources[0]=new ClassPathResource("admin.properties");
		
		conf.setLocations(resources);
		return conf;
	}
}
