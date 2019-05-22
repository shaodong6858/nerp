package cn.gs.util;

public class UUID {
	public final static String create () {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
}
