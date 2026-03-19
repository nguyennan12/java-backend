package com.javaweb.utils;

public class StringUtils {
	public static boolean checkString(String s) {
		if(s == null || s.equals("")) return false;
		else return true;
	}
}