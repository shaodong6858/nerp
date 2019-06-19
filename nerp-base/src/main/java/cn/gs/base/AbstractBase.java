package cn.gs.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.gs.base.util.SpringUtils;
import cn.gs.util.AESUtils;
import cn.gs.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * 所有commont的基类
 *
 * @author wangshaodong
 * 2019年05月13日
 */
@Slf4j
public abstract class AbstractBase implements IBaseInterface{

    /**
     * 自定义对象映射器
     */
    @Autowired
    public ObjectMapper objectMapper;
    
    /**
     * servlet应用上下文
     */
    @Autowired
    ServletContext servletContext;
    
    public HttpServletRequest getHttpRequest() {
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	if (requestAttributes == null) {
    		return null;
    	} else {
    		HttpServletRequest request = requestAttributes.getRequest();
    		return request;
    	}
    }
    
    /**
     * uri 模板处理
     */
    public UriTemplateHandler uriTemplateHandler = new DefaultUriBuilderFactory();
    
    /**
     * 将字符串解析为时间戳
     *
     * @param dateTime
     * @return
     */
    public Timestamp  parseTimestamp(String dateTime){
       DateTimeFormatter format = DateTimeFormat.forPattern(DATE_TIME_FORMAT_STRING); 
       return new Timestamp(DateTime.parse(dateTime, format).getMillis());
    }
    
    /**
     * 判断当前是否GET请求
     * @return
     */
    public boolean isGetRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod().equals(HTTP_REQUEST_METHOD_GET);
    }
    
    /**
     * 判断当前是否POST请求
     * @return
     */
    public boolean isPostRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod().equals(HTTP_REQUEST_METHOD_POST);
    }
    
    /**
     * 获取http请求对象
     * @return
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    /**
     * 获取http请求对象
     * @return
     */
    public boolean requestParameterContains(String key) {
    	return getRequest().getParameterMap().containsKey(key);
    }
    
    /**
     * 获取请求参数的map对象
     * @return
     */
    public Map<String, String> getRequestParameterMap() {
        Map<String, String> params = new HashMap<>();
        getRequest().getParameterMap().forEach((k,v) -> {
            params.put(k, v[0]);
        });
        return params;
    }

    /**
     * 获取http会话session
     */
    public HttpSession getHttpSession() {
        return getRequest().getSession();
    }

    /**
     * 今天的日期
     */
    public Date today() {
        return new Date(System.currentTimeMillis());
    }
    
    /**
     * 今天的日期
     */
    public Timestamp timeStampWithTimeAtStartOfDay() {
    	return new Timestamp(DateTime.now().withTimeAtStartOfDay().getMillis());
    }

    /**
     * 当前时间字符串
     * 日期格式："yyyy-MM-dd HH:mm:ss"
     */
    public String currentTime() {
        return DateTime.now().toString(DATE_TIME_FORMAT_STRING);  
    }
    
    /**
     * 当前日期字符串
     * 日期格式："yyyy-MM-dd"
     */
    public String currentDate() {
        return DateTime.now().toString(DATE_FORMAT_STRING);  
    }

    public Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 将bean 转换为map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> beanToMap(Object bean) {
        return objectMapper.convertValue(bean, Map.class);
    }
    
    /**
     * 将一个对象转换为指定类型的一个对象
     * @param fromValue
     * @param toValueType
     * @return
     */
    public <T> T objectToBean(Object fromValue, Class<T> toValueType) {
        try{
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.convertValue(fromValue, toValueType);
        }finally{
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        }
    }
    
    /**
     * 将http请求参数转换为指定类型的对象
     */
    public <T> T requestParameterToBean(Class<T> toValueType) {
        return objectToBean(getRequestParameterMap(), toValueType);
    }
    
    /**
     * 将一个实体bean转换到一个map对象
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> beanToMap(Object bean, Map<String, Object> model) {
        model.putAll(objectMapper.convertValue(bean, Map.class));
        return model;
    }

    /**
     * 用户构建hibernate查询参数
     */
    public HqlParamter params() {
        return new HqlParamter();
    }

    /**
     * 
     *  hibernate 参数化类
     * @author xiapengtao
     * 2017年4月25日
     */
    @SuppressWarnings("serial")
    public class HqlParamter extends HashMap<String, Object> {
        @Override
        public HqlParamter put(String key, Object value) {
            super.put(key, value);
            return this;
        }

        public HqlParamter setString(String key, String value) {
            super.put(key, value);
            return this;
        }

        public HqlParamter setInteger(String key, Integer value) {
            super.put(key, value);
            return this;
        }

        public HqlParamter setParameter(String key, Object value) {
            super.put(key, value);
            return this;
        }
        
    }

    /**
     * 用户承载后台传递到前台的数据类
     *
     * @author xiapengtao
     * 2017年4月25日
     */
    @SuppressWarnings("serial")
    public class DataMap extends HashMap<String, Object> {
        @Override
        public DataMap put(String key, Object value) {
            super.put(key, value);
            return this;
        }
    }
    
    /**
     * 新建一个后台向前台传递数据承载对象
     */
    public DataMap dataMap() {
        return new DataMap();
    }
    
    /**
     * 根据类名或类实例
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public  Class getClassByName(String clazz){
        try {
            return  Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            log.error("根据类名{}获取class出错", clazz, e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据类名或类的实例
     *
     * @param clazz
     * @return
     */
    public  Object getInstanceByClassName(String clazz){
        try {
            return  Class.forName(clazz).newInstance();
        } catch (Exception e) {
            log.error("根据类名{}获取类的实例", clazz, e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * gson 用户处理json对象
     */
    public Gson gson = getGson();
    
    public Gson getGson(){
    	GsonBuilder cb = new GsonBuilder();
    	cb.serializeSpecialFloatingPointValues();
    	return cb.create();
    }
    
    /**
     * 将对象转换为字符串
     */
    public String toJson(Object src) {
       return  gson.toJson(src);
    }
    
    /**
     * 将字符串转换为指定的类型
     */
    public <K> K fromJson(String json,  Class<K> classOfT) {
        return   gson.fromJson(json, classOfT);
    }
    
    /**
     * 将字符串转换为指定的类型
     * new TypeToken<T>() { }.getType()
     */
    public <K> K fromJson(String json, Type type) {
        return   gson.fromJson(json, type);
    }
    
    
    /**
     * Base64编码
     */
    public String base64Encode(String content) throws Exception {
        return base64Encode(content.getBytes(UTF8));
    }
    
    /**
     * Base64编码
     */
    public String base64Encode(byte[] bytes) throws Exception {
        return new String(Base64.getEncoder().encode(bytes));
    }
    
    /**
     * Base64解码
     */
    public byte[] base64Decode(String base64) throws Exception{
        return base64Decode(base64.getBytes(UTF8));
    }
    
    /**
     * Base64解码
     */
    public byte[] base64Decode( byte[] base64) throws Exception {
        return Base64.getDecoder().decode(base64);
    }
    
    
    /**
     * MD5加密
     */
    public String md5(String content){
        return MD5Utils.getMD5(content);
    }
    
    /**
     * AES加密
     */
    public String aesEncryptToBase64String(String content, String password) throws Exception{
        return base64Encode(AESUtils.encrypt(content, password));
    }
    
    /**
     * AES加密
     */
    public byte[] aesEncrypt(String content, String password){
        return AESUtils.encrypt(content, password);
    }
    
    /**
     * AES解密
     */
    public byte[] aesDecrypt(byte[] content, String password){
        return AESUtils.decrypt(content, password);
    }
    
    /**
     * 字符串模板替换
     * 变量域格式 {}
     * 输入formatStr("工号{}不存在", "1002");  
     * 输出：工号1002不存在
     */
    public  String formatStr(String template, String... strs){
        //处理null
        for (int i = 0; i < strs.length; i++) {
            if(strs[i] == null) strs[i] = ""; 
        }
        
        String regex = "\\{\\}"; // 非贪婪模式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);

        /*
         * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
         */
        StringBuffer sb = new StringBuffer();
        
        int i = 0;
        while (matcher.find()) {
            // 对替换内容进行预先处理 ,对于特殊含义字符"\","$" ,消除特殊意义(反向引用)
            matcher.appendReplacement(sb, Matcher.quoteReplacement(strs[i++]));
        }

        // 最后还得要把尾串接到已替换的内容后面去
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    
    @Value("${common.fileUploadPath:}")
    private String fileUploadPath;
    
    /**
     * 获取上传文件夹目录
     *
     * @return
     */
    public String getUploadDir()
    {
        File f = new File(fileUploadPath);
        if (!f.exists())
        {
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }
    
    /**
     * 获取上传文件夹目录
     *
     * @return
     */
    public String getUploadDir(String path)
    {
        return new File(getUploadDir(), path).getAbsolutePath();
    }
    
    /**
     * 根据日期生成上传文件相对路径
     * 输入：test.txt
     * 输出: 2016/10/12/test.txt
     * @return
     */
    public String generateFilePathByDate(String fileName)
    {
        return DateTime.now().toString("yyyy/MM/dd/") + fileName;
    }
    
    /**
     * 创建临时文件
     */
    public File createTempFile(String prefix, String suffix) throws IOException{
        return  File.createTempFile(prefix, suffix);
    }
    
    public String uuid() {
    	 return UUID.randomUUID().toString().replace("-", "");  
    }
    
    /**
     * 
     * 将文件作为附件写入HttpServletResponse输出流中
     * @param file
     * @param response
     * @throws IOException
     */
    public void writeFileAttachmentToResponse(File file, HttpServletResponse response) throws IOException{
        writeFileToResponse(file, file.getName(), false, response);
    }
    
    /**
     * 
     * 将文件写入HttpServletResponse输出流中
     * @param file
     * @param downloadType 文件下载类型 attachment inline
     * @param response
     * @throws IOException
     */
    public void writeFileToResponse(File file, String fileName, boolean isInline, HttpServletResponse response) throws IOException{
        response.reset();
        String mimeType = servletContext.getMimeType(fileName);
        response.setContentType(mimeType == null ? "applicatoin/octet-stream" : mimeType);    
        response.setHeader("Content-disposition", (isInline  ? CONTENT_DISPOSITION_INLINE  : CONTENT_DISPOSITION_ATTACHMENT) + "; filename=" + new String(fileName.getBytes(UTF8), ISO88591));   
        response.setContentLength((int)file.length());
        OutputStream out = response.getOutputStream();
        FileUtils.copyFile(file, out);
        out.close();
    }
    

    /**
     * 临时文件下载
     * @param fileName 临时文件名
     * @param response
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void writeTempFile(String fileName, HttpServletResponse response) throws UnsupportedEncodingException, IOException,
            FileNotFoundException {
        writeFileAttachmentToResponse(getTempFile(fileName), response);
    }
    
    public File getTempFile(String fileName) {
    	return new File(tempDir, fileName);
    }
    
    public Date plusDays(java.sql.Date date, int days){
    	date.setTime(date.getTime() + days * ONE_DAY_LONG);
    	return date;
    }
    
    public Date plusOneDay(java.sql.Date date){
    	return plusDays(date, 1);
    }
    
    public java.sql.Timestamp plusDays(java.sql.Timestamp timestamp, int days){
    	timestamp.setTime(timestamp.getTime() + days * ONE_DAY_LONG);
    	return timestamp;
    }
    
    public java.sql.Timestamp plusOneDay(java.sql.Timestamp timestamp){
    	return plusDays(timestamp, 1);
    }
    

    /** 
     * Date转换为LocalDateTime 
     * @param date 
     */  
    public LocalDateTime date2LocalDateTime(java.util.Date date){
    	 Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
    	return localDateTime;
    }
    
    /**
     * Date转换为LocalDateTime
     * @param date
     */
    public java.util.Date localDateTime2Date(LocalDateTime localDateTime) {
    	ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
    	return date;
    }
    
    /**
     * 获取时间戳的时
     * @param timestamp
     * @return
     */
    public int getHourOfDay(Timestamp timestamp) {
    	return new DateTime(timestamp.getTime()).getHourOfDay();
    }
    
    /**
     * 获取时间戳的时
     * @param timestamp
     * @return
     */
    public int getHourOfDay(java.util.Date date) {
    	return new DateTime(date.getTime()).getHourOfDay();
    }
    
    /**
     * 过滤mb4字符
     * @param in
     * @return
     */
    public String filterUtf8mb4(String str) {
		char end = '\u9FA5';
        final int LAST_BMP = 0xFFFF;
        return str.codePoints().filter(cp -> cp <= LAST_BMP).mapToObj(cp -> (char)cp < end ? (char)cp + "" : "").collect(Collectors.joining(""));
    }
    
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
 
    /**
     * 生成8位短uuid
     * @return
     */
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	

    /**
     * 判断类是否存在
     * @param name
     * @return
     */
    public boolean isClassPresent(String name) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    public ThreadPoolTaskScheduler getTaskScheduler () {
    	ThreadPoolTaskScheduler taskScheduler = SpringUtils.getBean("taskScheduler", ThreadPoolTaskScheduler.class);
    	return taskScheduler;
    }
    
    public String decodeToUtf8(String str) {
    	return new String(Base64.getDecoder().decode(str.getBytes()), StandardCharsets.UTF_8);
    }
    
    public String encodeToUtf8(String str) {
    	return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
    
    /**
     * 生成短8位uuid
     * @return
     */
    public String shortUUID() {
    	return RandomStringUtils.randomAlphanumeric(8);
    }
}
