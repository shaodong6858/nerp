package cn.gs.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 异或加密解密工具
 * @author wangshaodong
 * 2019年05月13日
 */
public class XORUtil {
	
	private static final String defaultSecretKey = "f80e6e8c-6227-41f9-9472-74e80fd35bbc";
	
	/**
	 * 异或加密解密
	 * @param secretKey
	 * @param input
	 * @return
	 */
	public static String encryptDecrypt(String secretKey, String input) {
		char[] key = secretKey.toCharArray(); //Can be any chars, and any length array
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < input.length(); i++) {
			output.append((char) (input.charAt(i) ^ key[i % key.length]));
		}
		return output.toString();
	}
	
	public static String encryptDecrypt(String input) {
		return encryptDecrypt(defaultSecretKey, input);
	}
	
	/**
	 * 从base64编码进行解析
	 * @param input
	 * @return
	 */
	public static String decryptFromBase64(String input) {
		input = new String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
		return encryptDecrypt(defaultSecretKey, input);
	}
	
	/**
	 * 加密后进行base64编码
	 * @param input
	 * @return
	 */
	public static String encryptToBase64(String input) {
		String cipher =  encryptDecrypt(defaultSecretKey, input);
		return new String (Base64.getEncoder().encodeToString(cipher.getBytes(StandardCharsets.UTF_8)));
	}
	
	public static void main(String[] args) {
//		String secretKey = "12345";
//		String encrypted = encryptToBase64("ceshi");
		System.out.println("Encrypted:" + Base64.getEncoder().encodeToString("08006".getBytes(StandardCharsets.UTF_8)));
		
		
		
//		String decrypted = XOREncryption.encryptDecrypt(secretKey, encrypted);
//		System.out.println("Decrypted:" + decrypted);
	}
}
