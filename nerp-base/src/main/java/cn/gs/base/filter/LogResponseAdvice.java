package cn.gs.base.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.google.gson.Gson;

import cn.gs.base.AbstractBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages = "cn.gs")
public class LogResponseAdvice extends AbstractBase implements ResponseBodyAdvice<Object> {
	
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
 
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    	HttpServletRequest httpServletRequest = ((ServletServerHttpRequest)request).getServletRequest();
    	httpServletRequest.setAttribute("_response_object_", body);
    	
    	String controllerName = returnType.getContainingClass().getSimpleName();
    	String controllerMethod =  returnType.getExecutable().getName();
//    	String method = httpServletRequest.getMethod().toLowerCase();
    	
    	String operation = controllerName + "." + controllerMethod;
    	httpServletRequest.setAttribute("_request_operation_", operation);
//    	log.info("执行方法：" + controllerName + ":" + controllerMethod);
//    	
//    	log.info("请求参数:{}", httpServletRequest.getRequestURI());
//    	log.info("请求参数:{}", new Gson().toJson(httpServletRequest.getParameterMap()));
//    	log.info("返回值为:{}", toJson(body));
        return body;
    }
    
}
