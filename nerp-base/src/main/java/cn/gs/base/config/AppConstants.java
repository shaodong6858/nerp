package cn.gs.base.config;

/**
 * 常量类， 定义在应用中经常使用的公共常量
 *
 * @author wangshaodong
 * 2019年05月13日
 */
public interface AppConstants {
	
	/**
	 * 产品名称
	 */
	String PRODUCT_NAME = "NERP";
	
	/**
	 * 产品版本
	 */
	String PRODUCT_VERSION = "1.0.1";

    /**
     * Unicode的可变长度字符编码
     */
    String UTF8 = "utf-8";
    
    /**
     * ISO8859-1字符编码
     */
    String ISO88591 =  "ISO8859-1";
    
    /**
     * Latin-1字符编码
     */
    String Latin1 = "Latin-1";
    
    /**
     * 请求成功响应结果字符串
     */
    String SUCCESS = "success";
    
    /**
     * 请求失败响应结果字符串
     */
    String ERROR = "error";
    
    /**
     * freemarker设置上下文路径，htm页面通过 ${base} 获取上下文路径
     * 部署在ROOT 下，此值为空
     */
    String CONTEXT_PATH = "base";
    
    /**
     * 配置文件:上传文件夹key
     */
    String CONFIG_FILE_UPLOAD_PATH = "fileUploadPath";
    
    
    /**
     * 应用服务器访问的项目url
     * 
     * 邮件发送会使用此url作为DataSourceUrlResolver解析图片资源
     */
    String[]  projectUrl = new String[1];
    
    
    /**
     * 临时文件夹目录
     */
    String tempDir = System.getProperty("java.io.tmpdir");
    
    
    /**
     * 日期格式："yyyy-MM-dd HH:mm:ss"
     */
    String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 日期格式："HH:mm:ss"
     */
    String TIME_FORMAT_STRING = "HH:mm:ss";
    
    /**
     * 日期格式："yyyy-MM-dd"
     */
    String DATE_FORMAT_STRING = "yyyy-MM-dd";
    
    long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
    
    /**
     * 文件下载类型
     * attachment：附件
     */
    String CONTENT_DISPOSITION_ATTACHMENT = "attachment";
    
    /**
     * 文件下载类型
     * attachment：内嵌显示
     */
    String CONTENT_DISPOSITION_INLINE = "inline";

    
    /**
     * http 请求方法
     */
    String HTTP_REQUEST_METHOD_GET = "GET";
    String HTTP_REQUEST_METHOD_POST = "POST";
    String HTTP_REQUEST_METHOD_PUT = "PUT";
    String HTTP_REQUEST_METHOD_DELETE = "DELETE";
    

    
    
}
