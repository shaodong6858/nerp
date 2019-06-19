package cn.gs.base.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求日志过滤器
* @author wangshaodong
 * 2019年05月13日
 */
@Slf4j
public class AccessLogFilter extends OncePerRequestFilter {
	
	private static Map<String, String> accessMappingMap = new HashMap<>();
	
	public Gson gson = new Gson();

	
	public AccessLogFilter() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		ServletContext sc = request.getServletContext();
		String queryString = request.getQueryString();
		String requestURI = request.getRequestURI();
		
		// 过滤掉静态资源请求访问
		if(requestURI.contains("swagger") 
        		|| requestURI.endsWith("api-docs") 
        		|| requestURI.endsWith(".html") 
        		|| requestURI.endsWith(".htm") 
        		|| requestURI.endsWith(".css") 
        		|| requestURI.endsWith(".js") 
        		|| requestURI.endsWith(".png") 
        		|| requestURI.endsWith(".jpg")){
        	filterChain.doFilter(request, response);
        	return;
        } 
		
		
		
		String t = requestURI;
		if (!StringUtils.isEmpty(sc.getContextPath())) {
			t = requestURI.replaceFirst(sc.getContextPath(), "");
		}
		
		String apiURI = t.substring(1);
		
		if(!HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			
			long requestTime = System.currentTimeMillis();
			
			// 获取header
			Enumeration<String> headerNams = request.getHeaderNames();
			Map<String, String> headerMap = new HashMap<>();
			
			while (headerNams.hasMoreElements()) {
				String headerName = headerNams.nextElement();
				String value = request.getHeader(headerName);
				headerMap .put(headerName, value);
//				System.out.println(String.format("请求头：%s : %s", headerName, request.getHeader(headerName)));
			}
			
			filterChain.doFilter(request, response);
			
			long responseTime = System.currentTimeMillis();
			
			Object responseObject = request.getAttribute("_response_object_");
			
			String operation = (String)request.getAttribute("_request_operation_");
			
			if (!"AccessLogController.search".equals(operation)) {
				
				if (operation != null && accessMappingMap.containsKey(operation)) {
					operation = accessMappingMap.get(operation);
				}
				
				String clientAddr = request.getRemoteAddr();
				
//				// 保存请求日志到数据库
//				AccessLog accessLog = new AccessLog();
//				accessLog.setAccessToken("");
//				accessLog.setRequestTime(new Timestamp(requestTime));
//				accessLog.setResponseTime(new Timestamp(responseTime));
//				accessLog.setElapsedTime(responseTime - requestTime);
//				accessLog.setHeaders(gson.toJson(headerMap));
//				accessLog.setParameters(gson.toJson(getRequestParameterMap(request)));
//				accessLog.setResult(gson.toJson(responseObject));
//				accessLog.setMethod(request.getMethod());
//				accessLog.setQueryString(queryString);
//				accessLog.setUri(apiURI);
//				accessLog.setOperation(operation == null ? apiURI : operation);
//				accessLog.setResponseStatus(response.getStatus());
//				accessLog.setClientAddr(clientAddr);
//				
//				// 设置创建人
////				accessLog.setCreateUserId(currentUser(request));
//				
//				// 异步保存请求日志
//				SpringUtils.getBean(AsyncAccessLogHandler.class).append(accessLog);
				
			}
			
		};
		
	}
	
//	public String currentUser(HttpServletRequest request) {
//		User user =  (User)request.getAttribute(User.ATTRIBUTE_PREFIX);
//		if (user == null) {
//			user = User.SYSTEM;
//		}
//		return user.getUsername();
//	}
	
	/**
     * 获取请求参数的map对象
     * @return
     */
    public Map<String, String> getRequestParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k,v) -> {
            params.put(k, StringUtils.join(v, ","));
        });
        return params;
    }
    
    public static void initMappingDataMap() {
//    	accessMappingMap = SpringUtils.getBean(GenericService.class).findAll(AccessMapping.class).stream().collect(Collectors.toMap(AccessMapping::getUri, AccessMapping::getOperation));
    }
    
}
