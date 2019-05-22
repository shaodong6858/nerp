package cn.gs.base;

import java.sql.Timestamp;

import org.joda.time.DateTime;

import cn.gs.base.config.AppConstants;

/**
 * component 根接口
 * 
 * @author xiapengtao 2016年5月12日
 */
public interface IBaseInterface extends AppConstants {
    
	
    /**
     * 格式化日期
     *
     * @param date
     * @param dataFormat
     * @return
     */
    default String  formatDate(String pattern){
        return DateTime.now().toString(pattern);
    }
    
    /**
     * 格式化日期
     *
     * @param date
     * @param dataFormat
     * @return
     */
    default String  formatDate(java.util.Date date, String pattern){
        return new DateTime(date).toString(pattern);
    }
    
    /**
     * 格式化日期
     *
     * @param date
     * @param dataFormat
     * @return
     */
    default String  formatDate(java.sql.Date date, String pattern){
        return new DateTime(date).toString(pattern);
    }
    
    /**
     * 格式化日期
     *
     * @param date
     * @param dataFormat
     * @return
     */
    default java.sql.Date  parseDate(long timeMillis){
        return  new java.sql.Date(timeMillis); 
    }
    
    /**
     * 格式化日期
     *
     * @param date
     * @param dataFormat
     * @return
     */
    default java.sql.Date  plusDays(java.sql.Date date, int days){
        return  new java.sql.Date(date.getTime() + days * 24 * 60 * 60 * 1000); 
    }
    
    /**
     * 当前的时间戳
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 间隔时长
     */
    public static long duration(Timestamp s, Timestamp e) {
        return s.getTime() - e.getTime();
    }
    
    /**
     * 间隔时长
     */
    public static long duration(Timestamp e) {
        return System.currentTimeMillis() - e.getTime();
    }
   
}