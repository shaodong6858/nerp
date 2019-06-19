package cn.gs.system;

import org.apache.commons.lang3.StringUtils;


public class test {
	static String staticSalt = ".shenhua";
    static String algorithmName = "MD5";
    static String encodedPassword = "123456";
    static String dynaSalt = "admin";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 4;
		int b = 2;
        System.out.println( (a&b)+((a^b)>>1));
	}

}
