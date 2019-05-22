package cn.gs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5消息摘要算法
 * @author wangshaodong
 * 2019年05月13日
 */
public class MD5Utils {
	
	private static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

//    private static final char[] DIGITS_UPPER =
//        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(str.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(DIGITS_LOWER[(bytes[i] >> 4) & 0x0f]);
            ret.append(DIGITS_LOWER[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
    
    
}
