package cn.gs.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import cn.gs.util.MD5Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * http请求工具类
 *
 * @author wangshaodong
 * 2019年05月13日
 */
@Slf4j
public class HttpUtils {
    
    /**
     * http 请求结果类
     *
     * @author wangshaodong
     * 2019年05月13日
     */
    @Data
    public static class HttpResult{
        
        //响应状态码
        private int responseCode;
        
        //响应内容类型
        private String contentType;
        
        //响应内容
        private String content;
        
        //错误响应内容
        private String errMsg;
        
        //内容长度
        private int  contentLength;
        
        //是否正常返回
        public boolean isHttpOK(){
            return responseCode == HttpURLConnection.HTTP_OK;
        }
        
        public SOAPMessage buildSoapMessage(){
            try {
                return  SoapUtil.getSoapMessageFromString(content);
            } catch (SOAPException | IOException e) {
                log.error("解析xml到soapMessage出错", e);
            }
            return null;
        }
        
    }
    
    
    /**
     * HttpUtils 请求参数配置
     *
      * @author wangshaodong
      * 2019年05月13日
     */
    public static class HttpConfig {
        private static final HttpConfig DEFAULT = custom();
        private boolean redirectsEnabled;
        private boolean basicAuthenticationEnabled;
        private String authCode;
        private String Cookie;
        private int connectTimeout;
        private int readTimeout;
        
        private boolean scertMD5SignatureEnabled;
        
        /** 秘钥参数名 **/
        private String secretKey;
        
        /** 秘钥 **/
        private String secret;
        
        /** URLEncoder.encode()**/        
        private String urlEnc;
        
        /** 响应内容编码 **/        
        private Charset charset;
        
        /** requestContentType **/        
        private String requestContentType;
        
        private Map<String, String> headers = new HashMap<>();

        public static HttpConfig custom() {
            return new HttpConfig();
        }

        private HttpConfig() {
            this.basicAuthenticationEnabled = false;
            this.connectTimeout = 10 * 1000;
            this.readTimeout = 30 * 1000;
            this.redirectsEnabled = true;
            this.scertMD5SignatureEnabled = false;
            this.urlEnc = StandardCharsets.UTF_8.name();
            this.charset = StandardCharsets.UTF_8;
            this.requestContentType = "application/x-www-form-urlencoded";
            setHeader("Accept", "*/*");
        }

        public HttpConfig setRedirectsEnabled(boolean redirectsEnabled) {
            this.redirectsEnabled = redirectsEnabled;
            return this;
        }

        public HttpConfig setBasicAuthenticationEnabled(boolean basicAuthenticationEnabled) {
            this.basicAuthenticationEnabled = basicAuthenticationEnabled;
            return this;
        }

        public HttpConfig setAuthCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public HttpConfig setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public HttpConfig setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public HttpConfig setSecretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public HttpConfig setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public HttpConfig setScertMD5SignatureEnabled(boolean scertMD5SignatureEnabled) {
            this.scertMD5SignatureEnabled = scertMD5SignatureEnabled;
            return this;
        }

        public HttpConfig setUrlEnc(String urlEnc) {
            this.urlEnc = urlEnc;
            return this;
        }
        
        public HttpConfig setCharset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public HttpConfig setRequestContentType(String requestContentType) {
            this.requestContentType = requestContentType;
            return this;
        }

        public HttpConfig setCookie(String cookie) {
            Cookie = cookie;
            return this;
        }
        
        public HttpConfig setHeader(String headerName, String value) {
        	headers.put(headerName, value);
        	return this;
        }
        
        public void configPOST(HttpURLConnection conn) throws ProtocolException {
        	this.config(conn);
        	conn.setRequestMethod(METHOD_POST); //设定请求方式POST
            conn.setDoInput(true);// 从服务器获取数据
            conn.setDoOutput(true);// 向服务器写入数据
        }
        
        public void configPUT(HttpURLConnection conn) throws ProtocolException {
        	this.config(conn);
        	conn.setRequestMethod(METHOD_PUT); //设定请求方式POST
        	conn.setDoInput(true);// 从服务器获取数据
        	conn.setDoOutput(true);// 向服务器写入数据
        }
        
        public void configGET(HttpURLConnection conn) throws ProtocolException {
        	this.config(conn);
        	conn.setRequestMethod(METHOD_GET); //设定请求方式POST
        }
        
        public void configDELETE(HttpURLConnection conn) throws ProtocolException {
        	this.config(conn);
        	conn.setRequestMethod(METHOD_DELETE); //设定请求方式POST
        }
        
        public void config(HttpURLConnection conn) {
        	conn.setConnectTimeout(this.connectTimeout); //设置连接超时为5秒
            conn.setReadTimeout(this.readTimeout); //设置响应超时为30秒
            conn.setInstanceFollowRedirects(this.redirectsEnabled);
            
            //设置basic认证
            if(this.basicAuthenticationEnabled){
                String code = Base64.getEncoder().encodeToString(this.authCode.getBytes(StandardCharsets.UTF_8));
                conn.setRequestProperty("Authorization", "Basic " + code);
            }
            
            //使用cookie，重用session
            if(this.Cookie != null){
                conn.setRequestProperty("Cookie", this.Cookie);
            }
            
            //添加请求头
            if (headers != null && !headers.isEmpty()) {
            	headers.forEach((k, v) -> {
            		conn.setRequestProperty(k, v);
            	});
            }
            
        }
        
    }
    
    /**
     *  针对http相应设置header
     *
     * @author xiapengtao
     * 2017年5月8日
     */
    @FunctionalInterface
    public interface Response{
        
        //设置内容长度 内容类型
        void setContentHeader(int contentLength, String contentType);
    }
    
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";

    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    
    /**
     * 判断url资源是否存在
     * @param addr
     * @return
     */
    public static boolean isUrlExist(String addr) {
    	return head(addr, null, HttpConfig.DEFAULT).isHttpOK();
    }
    
    /**
     * 
     * 使用 httpURLconnection执行http GET请求，返回结果
     *
     * @param addr   请求url
     * @return HttpResult
     */
    public static HttpResult head(String addr) {
        HttpURLConnection conn = null;
        HttpResult httpResult = new HttpResult();
        try {
               //创建URL对象
               URL url = new URL(addr); 

               //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
               conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod(METHOD_HEAD); //设定请求方式 get
               
               conn.connect(); //建立到远程对象的实际连接

               httpResult.responseCode = conn.getResponseCode();
               httpResult.contentType = conn.getContentType();
               httpResult.contentLength = conn.getContentLength();
               
        } catch (MalformedURLException e) {
            log.error("URL协议、格式或者路径错误", e);
            httpResult.errMsg = e.getLocalizedMessage();
        } catch (IOException e) {
            log.error("请求url:{}出错", addr, e);
            if(conn != null){
                try{
                    httpResult.errMsg = readStream(conn.getErrorStream(), StandardCharsets.UTF_8);
                }catch (Exception err) {
                    log.error("请求出错", err);
                }
            }
        } finally {
            //断开连接
            if (conn != null) conn.disconnect(); 
        }
        return httpResult;
    }
    
    /**
     * 
     * 使用 httpURLconnection执行http GET请求，返回结果
     *
     * @param addr   请求url
     * @param params   请求参数
     * @param httpConfig   请求http配置文件
     * @return
     */
    public static HttpResult head(String addr, Map<String, String> params, HttpConfig httpConfig) {
    	HttpURLConnection conn = null;
    	HttpResult httpResult = new HttpResult();
    	try {
    		//处理query param
    		StringBuilder sb = new StringBuilder();
    		buildParams(params, sb, httpConfig);
    		if(sb.length() != 0){
    			addr += (addr.contains("?") ? "&" : "?") + sb.toString();
    		}
    		//创建URL对象
    		URL url = new URL(addr); 
    		
    		//返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
    		conn = (HttpURLConnection) url.openConnection();
    		httpConfig.config(conn);
    		conn.setRequestMethod(METHOD_HEAD); //设定请求方式 get
    		
    		conn.connect(); //建立到远程对象的实际连接
    		
    		httpResult.responseCode = conn.getResponseCode();
    		httpResult.contentType = conn.getContentType();
    		httpResult.contentLength = conn.getContentLength();
    		
    	} catch (MalformedURLException e) {
    		log.error("URL协议、格式或者路径错误", e);
    		httpResult.errMsg = e.getLocalizedMessage();
    	} catch (IOException e) {
    		log.error("请求url:{}出错", addr, e);
    		if(conn != null){
    			try{
    				httpResult.errMsg = readStream(conn.getErrorStream(), httpConfig.charset); 
    			}catch (Exception err) {
    				log.error("请求出错", err);
    			}
    		}
    	} finally {
    		//断开连接
    		if (conn != null) conn.disconnect(); 
    	}
    	return httpResult;
    }
    
    
    /**
     * 
     * 使用 httpURLconnection执行http GET请求，返回结果
     *
     * @param addr   请求url
     * @param params   请求参数
     * @param httpConfig   请求http配置文件
     * @return
     */
    public static HttpResult get(String addr, Map<String, String> params, HttpConfig httpConfig) {
        HttpURLConnection conn = null;
        HttpResult httpResult = new HttpResult();
        try {
               //处理query param
               StringBuilder sb = new StringBuilder();
               buildParams(params, sb, httpConfig);
               if(sb.length() != 0){
                   addr += (addr.contains("?") ? "&" : "?") + sb.toString();
               }
               //创建URL对象
               URL url = new URL(addr); 

               //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
               conn = (HttpURLConnection) url.openConnection();
               httpConfig.configGET(conn);
               conn.setRequestMethod(METHOD_GET); //设定请求方式 get
               
               conn.connect(); //建立到远程对象的实际连接

               httpResult.responseCode = conn.getResponseCode();
               httpResult.contentType = conn.getContentType();
               httpResult.contentLength = conn.getContentLength();
               
               //创建一个BufferedReader，去读取结果流,得到的结果
               if(httpResult.isHttpOK()) {
            	   httpResult.content = readStream(conn.getInputStream(), httpConfig.charset); 
               }
               
        } catch (MalformedURLException e) {
            log.error("URL协议、格式或者路径错误", e);
            httpResult.errMsg = e.getLocalizedMessage();
        } catch (IOException e) {
            log.error("请求url:{}出错", addr, e);
            if(conn != null){
                try{
                    httpResult.errMsg = readStream(conn.getErrorStream(), httpConfig.charset); 
                }catch (Exception err) {
                    log.error("请求出错", err);
                }
            }
        } finally {
            //断开连接
            if (conn != null) conn.disconnect(); 
        }
        return httpResult;
    }
    
    public static HttpResult get(String addr) {
        return get(addr, null, HttpConfig.DEFAULT);
    }
    
    public static HttpResult get(String addr, HttpConfig httpConfig) {
        return get(addr, null, httpConfig);
    }
    
    public static HttpResult get(String addr, Map<String, String> params) {
        return get(addr, params, HttpConfig.DEFAULT);
    }
    
    /**
     * 
     * 使用 httpURLconnection执行http GET请求，返回结果
     *
     * @param addr   请求url
     * @param params   请求参数
     * @param httpConfig   请求http配置文件
     * @return
     */
    public static HttpResult delete(String addr, Map<String, String> params, HttpConfig httpConfig) {
    	HttpURLConnection conn = null;
    	HttpResult httpResult = new HttpResult();
    	try {
    		//处理query param
    		StringBuilder sb = new StringBuilder();
    		buildParams(params, sb, httpConfig);
    		if(sb.length() != 0){
    			addr += (addr.contains("?") ? "&" : "?") + sb.toString();
    		}
    		//创建URL对象
    		URL url = new URL(addr); 
    		
    		//返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
    		conn = (HttpURLConnection) url.openConnection();
    		httpConfig.configDELETE(conn);
    		conn.setRequestMethod(METHOD_GET); //设定请求方式 get
    		
    		conn.connect(); //建立到远程对象的实际连接
    		
    		httpResult.responseCode = conn.getResponseCode();
    		httpResult.contentType = conn.getContentType();
    		httpResult.contentLength = conn.getContentLength();
    		
    		//创建一个BufferedReader，去读取结果流,得到的结果
    		if(httpResult.isHttpOK()) {
    			httpResult.content = readStream(conn.getInputStream(), httpConfig.charset); 
    		}
    		
    	} catch (MalformedURLException e) {
    		log.error("URL协议、格式或者路径错误", e);
    		httpResult.errMsg = e.getLocalizedMessage();
    	} catch (IOException e) {
    		log.error("请求url:{}出错", addr, e);
    		if(conn != null){
    			try{
    				httpResult.errMsg = readStream(conn.getErrorStream(), httpConfig.charset); 
    			}catch (Exception err) {
    				log.error("请求出错", err);
    			}
    		}
    	} finally {
    		//断开连接
    		if (conn != null) conn.disconnect(); 
    	}
    	return httpResult;
    }
    
    public static HttpResult delete(String addr) {
    	return delete(addr, null, HttpConfig.DEFAULT);
    }
    
    public static HttpResult delete(String addr, HttpConfig httpConfig) {
    	return delete(addr, null, httpConfig);
    }
    
    public static HttpResult delete(String addr, Map<String, String> params) {
    	return delete(addr, params, HttpConfig.DEFAULT);
    }
    
    /**
     * 按照指定的编码读取流内容
     *
     * @param in
     * @param charset
     * @return
     */
    public static String readStream(InputStream in, Charset charset){
        if(in == null){
            log.error("输入流为null");
            return "";
        }
        return new BufferedReader(new InputStreamReader(in, charset)).lines().collect(Collectors.joining());
    }
    
    /**
     * 
     * 执行post请求
     * @param params
     * @return
     */
    public static HttpResult post(String addr){
        return post(addr, null, HttpConfig.DEFAULT);
    }
    
    /**
     * 
     * 执行post请求
     * @param params
     * @return
     */
    public static HttpResult post(String addr, Map<String, String> params){
        return post(addr, params, HttpConfig.DEFAULT);
    }
    
    /**
     * map 转成url参数拼接格式，例如 name=1001&password=100
     * 
     * @param params
     * @param sb
     * @param httpConfig
     */
    private static void  buildParams(Map<String, String> params, StringBuilder sb, HttpConfig httpConfig){
        if (params != null && !params.isEmpty()) {
            
            List<String> keyList = Arrays.asList(params.keySet().toArray(new String[0]));
            Collections.sort(keyList);
            String urlParam = keyList.stream().map(k -> {
				try {
					System.out.println(params);
					return k + "=" + URLEncoder.encode(params.get(k), httpConfig.urlEnc);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}).collect(Collectors.joining("&"));
            
            sb.append(urlParam);
            
            //判断是否需要签名
            if(httpConfig.scertMD5SignatureEnabled){
                String md5Result = MD5Utils.getMD5(sb.toString() + httpConfig.secret);
                sb.append(httpConfig.secretKey + "=" + md5Result);
            }
        }
    }
    
    
    /**
     * 
     * 执行post请求
     * @param params
     * @return
     */
    public static HttpResult postJson(String addr, String jsonBody, HttpConfig httpConfig) {
    	httpConfig.setRequestContentType("application/json");
    	return postInternal(addr, jsonBody, httpConfig);
    }
    
    /**
     * 
     * 执行post请求
     * @param params
     * @return
     */
    public static HttpResult postJson(String addr, String jsonBody) {
    	return postInternal(addr, jsonBody, HttpConfig.custom().setRequestContentType("application/json"));
    }
    
    /**
     * 
     * 执行post请求
     * @param params
     * @return
     */
    public static HttpResult post(String addr, Map<String, String> params, HttpConfig httpConfig) {
        StringBuilder sb = new StringBuilder();
        buildParams(params, sb, httpConfig);
        return postInternal(addr, sb.toString(), httpConfig);
        
    }

    /**
     * 执行post请求
     * @param addr
     * @param httpConfig
     * @param sb
     * @return
     */
	private static HttpResult postInternal(String addr, String body, HttpConfig httpConfig) {
		HttpURLConnection conn = null;
        HttpResult httpResult = new HttpResult();
        try {
               //创建URL对象
               URL url = new URL(addr); 

               //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
               conn = (HttpURLConnection) url.openConnection();
               httpConfig.configPOST(conn);
               
               // 获得上传信息的字节大小及长度
               byte[] mydata = body.getBytes(StandardCharsets.UTF_8);
               
               // 设置请求体的类型
               conn.setRequestProperty("Content-Type", httpConfig.requestContentType);
               conn.setRequestProperty("Content-Lenth", String.valueOf(mydata.length));

               conn.connect();
               
               // 获得输出流，向服务器输出数据
               conn.getOutputStream().write(mydata);

               httpResult.responseCode = conn.getResponseCode();
               httpResult.contentType = conn.getContentType();
               httpResult.contentLength = conn.getContentLength();
               
               //创建一个BufferedReader，去读取结果流,得到的结果
               httpResult.content = readStream(conn.getInputStream(), httpConfig.charset); 
               
        } catch (MalformedURLException e) {
            log.error("URL协议、格式或者路径错误", e);
            httpResult.errMsg = e.getLocalizedMessage();
        } catch (IOException e) {
            log.error("请求url:{}出错", addr, e);
            if(conn != null){
                try{
                    httpResult.errMsg = readStream(conn.getErrorStream(), httpConfig.charset); 
                }catch (Exception err) {
                    log.error("请求出错", err);
                }
            }
        } finally {
            //断开连接
            if (conn != null) conn.disconnect(); 
        }
        return httpResult;
	}
    
	
	 /**
     * 
     * 执行put请求
     * @param params
     * @return
     */
    public static HttpResult put(String addr, Map<String, String> params){
        return put(addr, params, HttpConfig.DEFAULT);
    }
    

    /**
     * 
     * 执行put请求
     * @param params
     * @return
     */
    public static HttpResult put(String addr, Map<String, String> params, HttpConfig httpConfig) {
        StringBuilder sb = new StringBuilder();
        buildParams(params, sb, httpConfig);
        return putInternal(addr, sb.toString(), httpConfig);
        
    }
	
	/**
	 * 执行put请求
	 * @param addr
	 * @param httpConfig
	 * @param sb
	 * @return
	 */
	private static HttpResult putInternal(String addr, String body, HttpConfig httpConfig) {
		HttpURLConnection conn = null;
		HttpResult httpResult = new HttpResult();
		try {
			//创建URL对象
			URL url = new URL(addr); 
			
			//返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
			conn = (HttpURLConnection) url.openConnection();
			httpConfig.configPUT(conn);
			
			// 获得上传信息的字节大小及长度
			byte[] mydata = body.getBytes(StandardCharsets.UTF_8);
			
			// 设置请求体的类型
			conn.setRequestProperty("Content-Type", httpConfig.requestContentType);
			conn.setRequestProperty("Content-Lenth", String.valueOf(mydata.length));
			
			conn.connect();
			
			// 获得输出流，向服务器输出数据
			conn.getOutputStream().write(mydata);
			
			httpResult.responseCode = conn.getResponseCode();
			httpResult.contentType = conn.getContentType();
			httpResult.contentLength = conn.getContentLength();
			
			//创建一个BufferedReader，去读取结果流,得到的结果
			httpResult.content = readStream(conn.getInputStream(), httpConfig.charset); 
			
		} catch (MalformedURLException e) {
			log.error("URL协议、格式或者路径错误", e);
			httpResult.errMsg = e.getLocalizedMessage();
		} catch (IOException e) {
			log.error("请求url:{}出错", addr, e);
			if(conn != null){
				try{
					httpResult.errMsg = readStream(conn.getErrorStream(), httpConfig.charset); 
				}catch (Exception err) {
					log.error("请求出错", err);
				}
			}
		} finally {
			//断开连接
			if (conn != null) conn.disconnect(); 
		}
		return httpResult;
	}
	
    /**
     * 
     * 发送soap报文
     * @param params
     * @return
     */
    public static HttpResult postSoap(String addr, String soapAction, String soapXML, HttpConfig httpConfig) {
        
        //设置请求内容类型
        httpConfig.setRequestContentType("text/xml;charset=UTF-8");
        
        HttpURLConnection conn = null;
        HttpResult httpResult = new HttpResult();
        try {
            //创建URL对象
            URL url = new URL(addr); 
            
            //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
            conn = (HttpURLConnection) url.openConnection();
            httpConfig.configPOST(conn);
            
            conn.setRequestProperty("SOAPAction", soapAction);
            
            // 获得上传信息的字节大小及长度
            byte[] mydata = soapXML.getBytes();
            
            // 设置请求体的类型
            conn.setRequestProperty("Content-Type", httpConfig.requestContentType);
            conn.setRequestProperty("Content-Lenth", String.valueOf(mydata.length));
            
            // 获得输出流，向服务器输出数据
            conn.getOutputStream().write(mydata);
            
            
            
            httpResult.responseCode = conn.getResponseCode();
            httpResult.contentType = conn.getContentType();
            httpResult.contentLength = conn.getContentLength();
            
            InputStream in = conn.getInputStream();
            if (in == null && httpResult.responseCode != HttpServletResponse.SC_OK) {
            	in = conn.getErrorStream();
            }
            
            if (in != null) {
            	//创建一个BufferedReader，去读取结果流,得到的结果
            	httpResult.content = readStream(in, httpConfig.charset);  
            }
            
        } catch (MalformedURLException e) {
            log.error("URL协议、格式或者路径错误,{}", addr, e);
            httpResult.errMsg = e.getLocalizedMessage();
        } catch (IOException e) {
            log.error("请求url:{}出错", addr, e);
            if(conn != null){
                try{
                    httpResult.errMsg =  readStream(conn.getErrorStream(), httpConfig.charset); 
                }catch (Exception err) {
                    log.error("请求出错", err);
                }
            }
        } finally {
            //断开连接
            if (conn != null) conn.disconnect(); 
        }
        return httpResult;
        
    }
    
    
    /**
     * 下载远程文件
     */
    public static void getHttpFile(String addr, String basicAuthCode, OutputStream output){
        getHttpFile(addr, basicAuthCode, null, output, null);
    }
    
    /**
     * 下载远程文件
     */
    public static void getHttpFile(String addr, OutputStream output){
    	getHttpFile(addr, null, output );
    }
    
    public static void getHttpFile(String addr, String basicAuthCode, HttpServletResponse response, OutputStream output, Map<String, String> headerMap){
        HttpURLConnection conn = null;
        try {
               //创建URL对象
               URL url = new URL(addr);

               //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
               conn = (HttpURLConnection) url.openConnection();
               conn.setConnectTimeout(HttpConfig.DEFAULT.connectTimeout); //设置连接超时为5秒
               conn.setReadTimeout(HttpConfig.DEFAULT.readTimeout); //设置响应超时为30秒
               conn.setRequestMethod(METHOD_GET); //设定请求方式 get
               
               //设置basic认证
               if(!StringUtils.isBlank(basicAuthCode)){
                   String code = Base64.getEncoder().encodeToString(basicAuthCode.getBytes(StandardCharsets.UTF_8));
                   conn.setRequestProperty("Authorization", "Basic " + code);
               }
               
               //设置请求头
               if(headerMap != null){
                   
                   for (String k : headerMap.keySet()) {
                         String v = headerMap.get(k);//得到每个key多对用value的值
                         conn.setRequestProperty(k, v);
                         System.out.println(k + "     " + v);
                     }
                   
               }
               
               System.out.println(conn.getRequestProperties());;
               
               conn.connect(); //建立到远程对象的实际连接

               
               //设置响应的header
               if(response != null){
                   
                   //设置响应码
                   response.setStatus(conn.getResponseCode());
                   
                   //设置响应头
                   Map<String, List<String>> headers = conn.getHeaderFields();
                   headers.forEach((k, v) -> {
                     if(k != null && v.size() > 0)  response.setHeader(k, v.get(0));
                   });
               }
               
//               System.out.println("##########@@@@@@@@");
//               conn.getHeaderFields().forEach((k,v) -> System.out.println(k + "  " + v));;
//               System.out.println("##########@@@@@@@@");
               IOUtils.copy(conn.getInputStream(), output);
               
               
        } catch (MalformedURLException e) {
            log.error("URL协议、格式或者路径错误", e);
        } catch (IOException e) {
            log.error("请求url:{}出错", addr, e);
        } finally {
            //断开连接
            if (conn != null) conn.disconnect(); 
        }
    }
    
    
    public static void main(String[] args) {
    	char a= (char) (Integer.MAX_VALUE - 1000);
    	System.out.println((int)a);
    	System.out.println(Integer.MAX_VALUE);
    }
    
}