package cn.gs.base;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  全局异常处理
 * @author wangshaodong
 * 2019年05月13日
 */
@RestControllerAdvice
@ApiIgnore
@Slf4j
@RestController
public class GlobalExceptionHandler extends AbstractController {
    
	/**
	 * 全局统一错误处理
	 * @param e
	 * @return
	 */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public HttpEntity<JsonResult> dealThrowable(Throwable e) {
//    	e.printStackTrace();
    	
    	// 获取顶层的
    	Throwable cause = e.getCause();
    	while (cause != null && cause.getCause() != null) {
    		cause = cause.getCause();
		}
    	
    	String message = cause.getLocalizedMessage();
    	
    	log.error("全局统一错误处理：{}", message, e);
    	return fail(message);
    }
    
    /**
     * 未认证
     */
    @RequestMapping("unauthorized")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public JsonResult unauthorized() {
    	return JsonResult.unauthorized();
    }
    
    /**
     * 拒绝访问
     */
    @RequestMapping("forbidden")
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public JsonResult forbidden() {
    	return JsonResult.forbidden();
    }
    
}