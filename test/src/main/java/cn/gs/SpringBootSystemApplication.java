package cn.gs;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.unicon.cas.client.configuration.EnableCasClient;
import tk.mybatis.spring.annotation.MapperScan;

//@EnableCasClient
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
@MapperScan("cn.gs.**.repository")
@SpringBootApplication(scanBasePackages="cn.gs")
public class SpringBootSystemApplication{
	
	private static ApplicationContext applicationContext = null;
	
//	@Bean
//    public Object testBean(PlatformTransactionManager platformTransactionManager){
//        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
//        return new Object();
//    }
//	@Bean
//	public SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
//	  SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
//	  configuration.setDataSource(dataSource);
//	  configuration.setTransactionManager(transactionManager);
//	  configuration.setDatabaseSchemaUpdate(SpringProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//	  configuration.setAsyncExecutorActivate(true);
////	  configuration.setJobManager(jobManager());
//	  configuration.setIdGenerator(new StrongUuidGenerator());
//	  return configuration;
//	}
//	@Bean
//	public BaseEntityEventListener setBaseEntityEventListener() {
//		return new BaseEntityEventListener();
//	}
//	@Bean
//	public ProcessEngine processEngine(SpringProcessEngineConfiguration processEngineConfiguration, BaseEntityEventListener eventListener) {
//		// 添加事件监听
//		processEngineConfiguration.setEventListeners(Arrays.asList(eventListener));
//		
//	    return processEngineConfiguration.buildProcessEngine();
//	}
//	@Bean
//	public RuntimeService getRuntimeService(ProcessEngine processEngine){
//		return processEngine.getRuntimeService();
//	}
//	
////	@Bean
////	public IdentityService getIdentityService(ProcessEngine processEngine){
////		return processEngine.getIdentityService();
////	}
//	
//	@Bean
//	public RepositoryService getRepositoryService(ProcessEngine processEngine){
//		return processEngine.getRepositoryService();
//	}
//	
//	@Bean
//	public TaskService getTaskService(ProcessEngine processEngine){
//		return processEngine.getTaskService();
//	}
//	
//	@Bean
//	public HistoryService getHistoryService(ProcessEngine processEngine){
//		return processEngine.getHistoryService();
//	}
	
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
