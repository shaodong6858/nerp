package cn.gs.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import cn.gs.base.util.SpringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2的配置文件
  * @author wangshaodong
 * 2019年05月13日
 */
@Configuration
@EnableSwagger2
public class Swagger2Config{
//swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("cn.gs"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                ;
        
    }
    
    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("access_token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }
    
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
    	String version = SpringUtils.getBean(Environment.class).getProperty("version");
        return new ApiInfoBuilder()
                //页面标题
                .title("nerp后台RESTful API")
                //创建人
                .contact(new Contact("王少东", "http://www.shenhua.com", ""))
                //版本号
                
                .version(version)
                //描述
                .description("新版nerp后台使用springboot开发")
                .build();
    }
    
}
 

