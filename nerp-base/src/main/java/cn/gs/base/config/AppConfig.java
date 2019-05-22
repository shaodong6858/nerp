package cn.gs.base.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import cn.gs.base.filter.AccessLogFilter;
import cn.gs.base.redis.RedisService;

/**
 * SpringBoot全局配置
  * @author wangshaodong
 * 2019年05月13日
 */
@SpringBootConfiguration
public class AppConfig {
	
	/** 
     * 跨域过滤器 
     * @return 
     */  
    @Bean  
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));  
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  
        return bean;
    } 
	
	/**
	 * 注册MDCInsertingServletFilter过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<MDCInsertingServletFilter> MDCInsertingServletFilterRegistration(RedisService redisService) {
	    FilterRegistrationBean<MDCInsertingServletFilter> registration = new FilterRegistrationBean<MDCInsertingServletFilter>();
	    registration.setFilter(new MDCInsertingServletFilter());
	    registration.addUrlPatterns("/*");
	    registration.setName("MDCInsertingServletFilter");
	    registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return registration;
	}
	
	/**
	 * 注册AccessLogFilter过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<AccessLogFilter> accessLogFilterRegistration() {
		FilterRegistrationBean<AccessLogFilter> registration = new FilterRegistrationBean<AccessLogFilter>();
		registration.setFilter(new AccessLogFilter());
		registration.addUrlPatterns("/*");
		registration.setName("AccessLogFilter");
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registration;
	}
	
	/**
	 * restTemplate 实例
	 * @param builder
	 * @return
	 */
    @Bean  
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
	
}
