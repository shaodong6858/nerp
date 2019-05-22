package cn.gs;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.gson.Gson;

import net.unicon.cas.client.configuration.EnableCasClient;

@EnableCasClient
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(scanBasePackages="cn.gs")
public class SpringBootSystemApplication{
	
	private static ApplicationContext applicationContext = null;
	
	@Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }
	
	public static ApplicationContext getApplicationContext () {
		return applicationContext;
	}
	
	/**
	 * 设置时区
	 */
	public static void setTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
	}
	
	public static void main(String[] args) {
		try {
			// 设置时区
			setTimeZone();
			
			System.out.println(new Date() + "boot start ");
			applicationContext = SpringApplication.run(SpringBootSystemApplication.class, args);
			System.out.println(new Date() + "" + applicationContext);
	//		String[] beanName = applicationContext.getBeanDefinitionNames();
	//		for (int i = 0; i < beanName.length; i++) {
	//			System.out.println(beanName[i] + " : " + applicationContext.getBean(beanName[i]));
	//		}
	//		System.out.println(applicationContext.getBean(EntityManagerFactory.class).unwrap(SessionFactory.class));
			System.out.println("boot end ");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
}
