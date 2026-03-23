package com.javaweb.utils;

public class StringUtils {
	public static boolean checkString(String s) {
		if(s == null || s.equals("")) return false;
		else return true;
	}
	
	public static String toSnakeCase(String str) {
	    if (str == null) return null;
	    String result = str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
	    
	    return result;
	}
}