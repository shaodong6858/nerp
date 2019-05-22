package cn.gs.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static void main(String args[]) {
        HashMap data = new HashMap();
        String template = "尊敬的客户${customerName}你好！本次消费金额${amount}，" + "您帐户${accountNumber}上的余额为${balance}，欢迎下次光临！";
        data.put("customerName", "刘明");
        data.put("accountNumber", "888888888");
        data.put("balance", "$1000000.00");
        data.put("amount", "$1000.00");
        try {
            System.out.println(composeMessage(template, data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模板替换
     * 
     * 变量域格式 ${name}
     * 
     * @param template
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static String composeMessage(String template, Map data) throws Exception {
        String regex = "\\$\\{(.+?)\\}"; // 非贪婪模式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);

        /*
         * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
         */
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1);// 键名
            String value = (String) data.get(name);// 键值
            if (value == null) {
                value = "$0"; //反向引用根分组所匹配内容
            }else{
                // 对替换内容进行预先处理 ,对于特殊含义字符"\","$" ,消除特殊意义(反向引用)
                value = Matcher.quoteReplacement(value);
            }

            matcher.appendReplacement(sb, value);
            
            //输出转换后的内容
//            System.out.println("sb = " + sb.toString());
        }

        // 最后还得要把尾串接到已替换的内容后面去
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    
    /**
     * 模板替换
     * 
     * 变量域格式 ${name}
     * 
     * @param template
     * @param data
     * @return
     * @throws Exception
     */
    public static String removePrefix(String template) throws Exception {
        String regex = "\\$\\{(.+?)\\}"; // 非贪婪模式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        
        /*
         * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
         */
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1);// 键名
            
            matcher.appendReplacement(sb, " {"+name+"} ");
            
        }
        
        // 最后还得要把尾串接到已替换的内容后面去
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    
    
}
